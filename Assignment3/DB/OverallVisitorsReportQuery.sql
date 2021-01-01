SELECT orderType, SUM(amountArrived), arrivedTime
 FROM g9_gonature.orders
 WHERE parkName='jurasic' AND arrivedTime BETWEEN '2020-12-28' AND '2021-01-03' AND amountArrived >0
 GROUP BY orderType;