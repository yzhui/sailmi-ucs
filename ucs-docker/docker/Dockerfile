# Docker image for springboot file run
# VERSION 0.0.1
# Author yzh
# 基础镜像使用java
FROM tomcat
# 作者
MAINTAINER yzh yinzhaohui@126.com
# VOLUME 指定了临时文件目录为tmp。
# 其效果是在主机 varlibdocker 目录下创建了一个临时文件，并链接到容器的tmp
VOLUME tmp
# 将jar包添加到容器中
WORKDIR /ucs-apps/
ADD ucs-apps /ucs-apps/
ADD ucs-web /usr/local/tomcat/webapps/ucs
RUN chmod +x /ucs-apps/startUcs.sh

#RUN chkconfig --add /ucs-apps/startUcs.sh\
#    &&chkconfig /ucs-apps/startUcs.sh on
ENTRYPOINT ["/ucs-apps/startUcs.sh"]
# 运行ucs服务
#ENTRYPOINT ["/opt/jdk/bin/java -version"]
