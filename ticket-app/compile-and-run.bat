
call mvnw.cmd clean compile package -Dmaven.test.skip=true


start javaw -jar target/ticket-app-0.0.1-SNAPSHOT-exec.jar