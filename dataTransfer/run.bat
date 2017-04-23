CHCP 65001
java -jar -Dfile.encoding=UTF-8 dataTransfer-0.0.1-SNAPSHOT.jar ^
% 源库SQLSERVER连接信息 SQLFB%
--spring.datasource.from.url="jdbc:sqlserver://192.168.80.253:1433;DatabaseName=sqlfb" ^
--spring.datasource.from.username="sa" ^
--spring.datasource.from.password="admin123" ^
 ^
% 目标库MYSQL连接信息 CWLW%
--spring.datasource.to_cwlw.url="jdbc:mysql://192.168.1.16:3306/cwlw?useUnicode=true&characterEncoding=utf-8&useSSL=false" ^
--spring.datasource.to_cwlw.username="root" ^
--spring.datasource.to_cwlw.password="root" ^
 ^
% 目标库数据字典MYSQL连接信息 GT%
--spring.datasource.to_gt.url="jdbc:mysql://192.168.1.16:3306/gt?useUnicode=true&characterEncoding=utf-8&useSSL=false" ^
--spring.datasource.to_gt.username="root" ^
--spring.datasource.to_gt.password="root" ^
 ^
--myini.lkbh=8888