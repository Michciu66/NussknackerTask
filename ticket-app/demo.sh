#!/bin/bash
red=$(tput setaf 1)
blue=$(tput setaf 4)
normal=$(tput sgr0)
current_date_time=$(date +"%Y-%m-%d %H:%M")


printf "${red}Beginning demo \n\n"
printf "Getting screenings from ${normal}$current_date_time \n\n"

curl --get --data-urlencode "startDate=${current_date_time}" \
    --location "http://localhost:8080/screenings/byDate?"

printf "\n\n${red}Getting information about screening ${normal}3\n\n"
curl --location 'http://localhost:8080/screenings/3'

printf "\n\n${red}Sending reservation for${normal} Michał Chudzik${red}, for screening with ID${normal} 3${red}, for two adults in seats ${normal} 1 and 2${red} in row ${normal} 1\n\n"
curl --location --request PUT 'localhost:8080/reservations/create' \
--header 'Content-Type: application/json' \
--data '{
        "clientName": "Michał",
        "clientSurname": "Chudzik",
        "screeningId": 1,
        "tickets": [
            {
                "seatNumber":1,
                "seatRow":1,  
                "ticketType":"ADULT"
            },
            {
                "seatNumber":2,
                "seatRow":1,  
                "ticketType":"ADULT"
            }
        ]
}
'

printf "\n\n${red}End of demo ${normal}\n"
