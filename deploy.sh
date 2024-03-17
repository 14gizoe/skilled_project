REPOSITORY=/home/ubuntu/app
# linux => /home/ubuntu/app == window => ./app
cd $REPOSITORY

APP_NAME=skilled_project

#ls $REPOSITORY/build/libs/: $REPOSITORY/build/libs/ 디렉토리에 있는 파일 목록을 나열합니다. 여기서 $REPOSITORY는 환경 변수로 설정된 경로를 가리킵니다. 즉, $REPOSITORY/build/libs/는 빌드된 JAR 파일이 위치한 디렉토리입니다.
#
#grep '.jar': ls 명령의 결과 중에서 '.jar'로 끝나는 파일만 필터링합니다. 이렇게 하면 JAR 파일만을 대상으로 다음 단계를 진행할 수 있습니다.
#
#tail -n 1: 필터링된 결과 중에서 마지막 줄만 선택합니다. 이렇게 하면 가장 최신의 JAR 파일이 선택됩니다.

JAR_NAME=$(ls $REPOSITORY | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/$JAR_NAME

CURRENT_PID=$(pgrep -f APP_NAME)

if [ -z $CURRENT_PID ] #2
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  sleep 5
fi

# java가 깔려있지 않으면 java 설치
if ! command -v java &> /dev/null
then
    echo "Java is not installed. Installing..."
    # Install Java (OpenJDK 11)
    sudo apt update
    sudo apt install -y openjdk-17-jdk
    # Verify Java installation
    java -version
else
    echo "Java is already installed."
fi

echo "> $JAR_PATH 배포" #3
nohup java -jar $JAR_NAME &
