SELECT parkName,arrivedTime,SUM(visitorsNumber) FROM (
(SELECT parkName,arrivedTime, SUM(visitorsNumber-amountArrived) as visitorsNumber
 FROM g9_gonature.orders
 WHERE arrivedTime BETWEEN '2020-12-28' AND '2021-01-03' AND visitorsNumber-amountArrived>0
 GROUP BY DAY(arrivedTime), parkName)
 UNION ALL
 (SELECT parkName,arrivedTime, sum(visitorsNumber) as visitorsNumber
 FROM g9_gonature.canceledorders
 WHERE arrivedTime BETWEEN '2020-12-20' AND '2021-01-03'
 GROUP BY DAY(arrivedTime), parkName)) t1


