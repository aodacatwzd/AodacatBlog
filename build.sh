echo "========正在进入tomcat/bin目录========"
cd /usr/apache-tomcat-9.0.20/bin/

echo "========正在关闭tomcat服务========"
./shutdown.sh


echo "========正在进入tomcat/webapps目录========"
cd /usr/apache-tomcat-9.0.20/webapps/

echo "========正在进入删除ROOT目录和ROOT.war========"
rm -rf ROOT
rm -rf ROOT.war

echo "========正在进入AodacatBlog目录========"
cd /usr/AodacatBlog/

echo "========正在拉取项目========"
git pull

echo "========正在复制文件ROOT.war========"
cd ./target
cp demo-0.0.1-SNAPSHOT.war /usr/apache-tomcat-9.0.20/webapps/ROOT.war

echo "========正在进入tomcat/bin目录========"
cd /usr/apache-tomcat-9.0.20/bin/

echo "========启动tomcat========"
./startup.sh
