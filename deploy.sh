REPOSITORY=/home/ubuntu/app
# linux => /home/ubuntu/app == window => ./app
cd $REPOSITORY

APP_NAME=skilled_project

JAR_NAME=$(ls $REPOSITORY | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)

if [ -z "$CURRENT_PID" ] #2
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 "$CURRENT_PID"
  sleep 5
fi

# java가 깔려있지 않으면 java 설치
if ! command -v java &> /dev/null
then
    echo "java가 설치되어있지않아 설치합니다."
    # Install Java (OpenJDK 11)
    sudo apt-get update
    sudo apt-get install -y openjdk-17-jdk
    # Verify Java installation
    java -version
else
    echo "java는 이미 설치되어있습니다."
fi

echo "> $JAR_PATH 배포" #3
nohup java -jar "$JAR_NAME" > $REPOSITORY/nohup.out 2>&1 &
