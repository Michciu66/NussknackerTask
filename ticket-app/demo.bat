@echo off
for /f "delims=" %%a in ('wmic OS Get localdatetime  ^| find "."') do set dt=%%a
set datetimef=%dt:~0,4%-%dt:~4,2%-%dt:~6,2% %dt:~8,2%:%dt:~10,2%

echo [91mBeginning Demo[0m
echo:
echo [91mGetting screenings from %datetimef%[0m
echo:
curl --get --data-urlencode "startDate=%datetimef%" --location "http://localhost:8080/screenings/byDate?"

echo:
echo [91mGetting information about screening 3[0m
echo:
curl --location "http://localhost:8080/screenings/3"

echo:
echo [91mSending reservation for Michał Chudzik, for screening with ID 3, for two adults in seats 1 and 2 in row 1[0m
echo:
curl -d "@demoTickets.json" --request PUT "http://localhost:8080/reservations/create" --header "Content-Type: application/json"

echo:
echo [91mEnd of demo[0m
echo:

pause