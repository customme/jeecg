#!/bin/bash
# 初始化收入支出


# 执行sql
function exec_sql()
{
    local sql="${1:-`cat`}"
    local params="${2:--s -N --local-infile}"

    echo "SET NAMES utf8;$sql" | mysql -uroot $db_name $params
}

function main()
{
    db_name="$1"

    # 商品成本
    echo "SELECT create_by, DATE(buy_time) buy_date, SUM(actual_amount) amount FROM ps_receipt GROUP BY create_by, buy_date HAVING amount > 0;" | exec_sql |
    awk '{
        printf("INSERT INTO ps_account(create_by, biz_date, goods_cost) VALUES(\"%s\", \"%s\", %s) ON DUPLICATE KEY UPDATE goods_cost = %s;\n",$1,$2,$3,$3)
    }' | exec_sql

    # 订单收入
    echo "SELECT create_by, DATE(create_date) order_date, SUM(price + freight - discount_amount) amount, SUM(paid) FROM ps_order GROUP BY create_by, order_date HAVING amount > 0;" | exec_sql |
    awk '{
        printf("INSERT INTO ps_account(create_by, biz_date, receivable, received) VALUES(\"%s\", \"%s\", %s, %s) ON DUPLICATE KEY UPDATE receivable = %s,received = %s;\n",$1,$2,$3,$4,$3,$4)
    }' | exec_sql

    # 其他成本
    echo "SELECT a.create_by, a.cost_date, SUM(IF(a.currency = 0, a.cost, a.cost * b.max_rate)) FROM ps_cost a INNER JOIN (SELECT DATE(update_time) update_date, src_cur, MAX(rate) max_rate FROM ps_rate WHERE tar_cur = 0 GROUP BY DATE(update_time), src_cur) b ON a.cost_date = b.update_date GROUP BY a.create_by, a.cost_date;" | exec_sql |
    awk '{
        printf("INSERT INTO ps_account(create_by, biz_date, expense) VALUES(\"%s\", \"%s\", %s) ON DUPLICATE KEY UPDATE expense = %s;\n",$1,$2,$3,$3)
    }' | exec_sql
}
main "$@"