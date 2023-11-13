package com.chudzik.ticketapp;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.chudzik.DemoData;
import com.chudzik.dtos.ReservationCreationDto;
import com.chudzik.dtos.ReservationDto;
import com.chudzik.enums.TicketType;
import com.chudzik.mappers.ReservationMapper;
import com.chudzik.models.Reservation;
import com.chudzik.models.Screening;

import com.chudzik.records.ReservationInfo;
import com.chudzik.records.TicketInfo;
import com.chudzik.repositories.MovieRepository;
import com.chudzik.repositories.ReservationRepository;
import com.chudzik.repositories.RoomRepository;
import com.chudzik.repositories.ScreeningRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ReservationControllerTests {

    @Autowired
    private MovieRepository movRepo;
    @Autowired
    private ReservationRepository resRepo;
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private ScreeningRepository screenRepo;
    @Autowired
    private DemoData demoData;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String RESERVATIONS_PATH = "/reservations";
    private static final String RESERVATIONSCREATION_PATH = RESERVATIONS_PATH + "/create";

    @AfterEach
    @Transactional
    public void resetRepos() {
        movRepo.deleteAll();
        movRepo.flush();
        resRepo.deleteAll();
        resRepo.flush();
        roomRepo.deleteAll();
        roomRepo.flush();
        screenRepo.deleteAll();
        screenRepo.flush();
    }

    private void prepareReservationsRepo() {
        Screening screening = screenRepo.getReferenceById(1L);
        Reservation reservation = new Reservation("Michał", "Chudzik",
                screening, screening.getRoom());
        reservation.addSeat(1, 1, TicketType.ADULT);
        reservation.addSeat(2, 1, TicketType.CHILD);
        resRepo.save(reservation);
        screening.addReservation(resRepo.findByClientName("Michał"));

        reservation = new Reservation("Jan", "Kowalski",
                screening, screening.getRoom());
        reservation.addSeat(5, 1, TicketType.ADULT);
        reservation.addSeat(6, 1, TicketType.CHILD);
        resRepo.save(reservation);
        screening.addReservation(resRepo.findByClientName("Jan"));
    } // Row 1 in Screening 1: 1,1,0,0,1,1,0,0,0,0
      // 1 - Reserved, 0 - Unreserved seat

    @BeforeEach
    @Transactional
    public void prepareRepositories() {
        demoData.prepareData();
        prepareReservationsRepo();
    }

    @Test
    void testGetAllReservations() throws Exception {

        // when
        MvcResult result = mockMvc.perform(get(RESERVATIONS_PATH))
                .andReturn();
        String json = result.getResponse().getContentAsString();
        ArrayNode node = (ArrayNode) objectMapper.readTree(json).get("_embedded").get("reservationDtoList");
        List<ReservationDto> reservationList = objectMapper.readerFor(new TypeReference<List<ReservationDto>>() {
        }).readValue(node);

        List<ReservationDto> repoList = resRepo.findAll().stream()
                .map(ReservationMapper::convertToDto)
                .toList();

        // then
        assertEquals(reservationList, repoList);
    }

    @Test
    void testGetOneReservation() throws Exception {
        // given
        Long id = resRepo.findAll().get(0).getId();
        ReservationDto reservation = ReservationMapper.convertToDto(resRepo.findAll().get(0));

        // when
        MvcResult result = mockMvc.perform(get(RESERVATIONS_PATH + "/{id}", id))
                .andExpect(status().isOk())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ReservationDto foundReservation = objectMapper.readValue(json, ReservationDto.class);

        // then
        assertEquals(reservation, foundReservation);
    }

    @Test
    void testGetNonexistentReservation() throws Exception {
        // given
        Long id = Long.MAX_VALUE;

        // when
        mockMvc.perform(get(RESERVATIONS_PATH + "/{id}", id))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateReservation() throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 2, TicketType.ADULT),
                new TicketInfo(2, 2, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 1L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        MvcResult result = mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        ReservationCreationDto reservationCreation = objectMapper.readValue(json, ReservationCreationDto.class);
        Screening screening = screenRepo.getReferenceById(1L);

        // then
        assertEquals(reservationCreation.getCost(), BigDecimal.valueOf(5000, 2));
        assertEquals(reservationCreation.getTime(), screening.getTime().plusMinutes(30));
    }

    @Test
    void testCreateReservationTwice() throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 2, TicketType.ADULT),
                new TicketInfo(2, 2, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 1L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());

        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateReservationCreatingGap() throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 2, TicketType.ADULT),
                new TicketInfo(3, 2, TicketType.ADULT));
        // Row 2 before addition is empty, adding these seats would create an unreserved
        // single seat between two reserved seats
        // so it is discarded.
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 1L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = { 3, 1 })
    void testCreateReservationWithIncorrectSeats(int seatNumber) throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(seatNumber, 1, TicketType.ADULT));
        // Row 1 in Screening 1: 1,1,1,0,1,1,0,0,0,0 - would create gap in seat 4
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 1L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateReservationInNonexistantSeat() throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(20, 20, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 1L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateReservationUsingInvalidScreening() throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 1, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 7L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = { "A", "",
            "LoremipsumdolorsitametIdgalisumducimusestfuganostrumetducimusarchitectoquidolorsaepevelsolutaofficiaestaliquideveniet",
            "Foo-bar", "Foo-B",
            "This is a test with whitespace in it", "Andothersymbols&*$#*", "ALLCAPS" })
    @NullSource
    void testCreateReservationWithIncorrectSurname(String surname) throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 1, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo("John", surname, 7L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(strings = { "A", "",
            "LoremipsumdolorsitametIdgalisumducimusestfuganostrumetducimusarchitectoquidolorsaepevelsolutaofficiaestaliquideveniet",
            "This is a test with whitespace in it", "Andothersymbols&*$#*", "ALLCAPS" })
    void testCreateReservationWithIncorrectName(String name) throws Exception {
        // given
        List<TicketInfo> tickets = List.of(new TicketInfo(1, 1, TicketType.ADULT));
        ReservationInfo reservationInfo = new ReservationInfo(name, "Doe", 7L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateReservationUsingEmptyTicketList() throws Exception {
        // given
        List<TicketInfo> tickets = new ArrayList<>();
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 7L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testCreateReservationUsingNullTicketList() throws Exception {
        // given
        List<TicketInfo> tickets = null;
        ReservationInfo reservationInfo = new ReservationInfo("John", "Doe", 7L, tickets);
        String requestJson = objectMapper.writeValueAsString(reservationInfo);

        // when
        mockMvc.perform(put(RESERVATIONSCREATION_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                // then
                .andExpect(status().isBadRequest());
    }
}
