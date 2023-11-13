package com.chudzik;

import java.time.LocalDateTime;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.chudzik.models.Movie;
import com.chudzik.models.Room;
import com.chudzik.models.Screening;
import com.chudzik.models.Seat;
import com.chudzik.repositories.MovieRepository;
import com.chudzik.repositories.RoomRepository;
import com.chudzik.repositories.ScreeningRepository;

import jakarta.transaction.Transactional;

@Component
@Transactional
public class DemoData {
    private final MovieRepository movRepo;
    private final RoomRepository roomRepo;
    private final ScreeningRepository screenRepo;

    public DemoData(MovieRepository movRepo, RoomRepository roomRepo, ScreeningRepository screenRepo) {
        this.movRepo = movRepo;
        this.roomRepo = roomRepo;
        this.screenRepo = screenRepo;
    }


    public void prepareMovieRepo() {
		movRepo.save(new Movie("Superhero Movie", 120, "Superhero Movie Description"));
		movRepo.save(new Movie("Drama Movie", 120, "Drama Movie Description"));
		movRepo.save(new Movie("Romance Movie", 120, "Romance Movie Description"));
	}

	public void prepareRoomRepo() {
		for (int k = 1; k <= 3; k++) {
			Room room = new Room(k);

			for (int i = 1; i <= 10; i++) // seat row
			{
				for (int j = 1; j <= 10; j++) // seat number
				{
					Seat seat = new Seat(i, j);
					room.getSeatList().add(seat);
				}
			}
			roomRepo.save(room);
		}
	}

	public void prepareScreeningRepo() {

		screenRepo.save(new Screening(LocalDateTime.now().plusHours(3), movRepo.getReferenceById(1L),
                roomRepo.getReferenceById(1L))); // Screening Superhero Movie in three hours in room 1
        screenRepo.save(new Screening(LocalDateTime.now().plusHours(4), movRepo.getReferenceById(2L),
                roomRepo.getReferenceById(1L))); // Screening Romance Movie in four hours in room 1
        screenRepo.save(new Screening(LocalDateTime.now().plusHours(5), movRepo.getReferenceById(2L),
                roomRepo.getReferenceById(2L))); // Screening Drama Movie in five hours in room 2
        screenRepo.save(new Screening(LocalDateTime.now().plusHours(3), movRepo.getReferenceById(2L),
                roomRepo.getReferenceById(2L))); // Screening Drama Movie in three hours in room 2
        screenRepo.save(new Screening(LocalDateTime.now().plusMinutes(20), movRepo.getReferenceById(3L),
                roomRepo.getReferenceById(3L))); // Screening Romance Movie in 20 minutes in room 3
        screenRepo.save(new Screening(LocalDateTime.now().plusHours(1), movRepo.getReferenceById(3L),
                roomRepo.getReferenceById(3L))); // Screening Romance Movie in one hour in room 3
        screenRepo.save(new Screening(LocalDateTime.now().minusHours(3), movRepo.getReferenceById(1L),
                roomRepo.getReferenceById(1L))); // Screening Superhero Movie three hours ago in room 1
	}

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        prepareData();
    }
    
    public void prepareData()
    {
        prepareMovieRepo();
        prepareRoomRepo();
        prepareScreeningRepo();
    }
}
