@echo off
echo Building auth-service...
cd auth-service
call mvn clean package -DskipTests
cd ..

echo Building account-service...
cd account-service
call mvn clean package -DskipTests
cd ..

echo Building api-gateway...
cd api-gateway
call mvn clean package -DskipTests
cd ..

echo Building frontend...
cd frontend
call npm run build
cd ..

echo Done! Run 'docker-compose up -d --build' to start all services.