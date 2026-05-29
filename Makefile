.PHONY: build run stop test report clean

build:
	cd auth-service && mvn clean package -DskipTests
	cd account-service && mvn clean package -DskipTests

run:
	docker-compose up -d --build

stop:
	docker-compose down

test:
	cd aqa-tests && mvn clean test

report:
	cd aqa-tests && mvn allure:serve

clean:
	docker-compose down -v
	rm -rf auth-service/target account-service/target aqa-tests/target