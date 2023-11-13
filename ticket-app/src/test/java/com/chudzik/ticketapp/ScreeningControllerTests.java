package com.chudzik.ticketapp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.chudzik.DemoData;
import com.chudzik.comparators.ScreeningComparator;
import com.chudzik.dtos.ScreeningDto;
import com.chudzik.mappers.ScreeningMapper;
import com.chudzik.repositories.MovieRepository;
import com.chudzik.repositories.RoomRepository;
import com.chudzik.repositories.ScreeningRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ScreeningControllerTests {

	@Autowired
	private MovieRepository movRepo;
	@Autowired
	private RoomRepository roomRepo;
	@Autowired
	private ScreeningRepository screenRepo;
	@Autowired
	DemoData demoData;

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	private static final String SCREENINGS_PATH = "/screenings";

	@AfterEach
	public void resetRepos() {
		movRepo.deleteAll();
		movRepo.flush();
		roomRepo.deleteAll();
		roomRepo.flush();
		screenRepo.deleteAll();
		screenRepo.flush();
	}

	@BeforeEach
	public void prepareScreeningRepo() {
		demoData.prepareData();
	}

	@Test
	void testGetAllScreenings() throws Exception {

		// when
		MvcResult result = mockMvc.perform(get(SCREENINGS_PATH))
				.andReturn();
		String json = result.getResponse().getContentAsString();
		ArrayNode node = (ArrayNode) objectMapper.readTree(json).get("_embedded").get("screeningDtoList");
		List<ScreeningDto> screeningList = objectMapper.readerFor(new TypeReference<List<ScreeningDto>>() {
		}).readValue(node);

		List<ScreeningDto> repoList = screenRepo.findAll().stream()
				.map(ScreeningMapper::convertToLimited)
				.sorted(new ScreeningComparator())
				.collect(Collectors.toList());

		// then
		assertEquals(screeningList, repoList);
	}

	@Test
	void testGetOneScreening() throws Exception {
		// given
		Long id = screenRepo.findAll().get(0).getId();
		ScreeningDto screening = ScreeningMapper.convertToDto(screenRepo.findAll().get(0));
		// when
		MvcResult result = mockMvc.perform(get(SCREENINGS_PATH + "/{id}", id))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ScreeningDto foundScreening = objectMapper.readValue(json, ScreeningDto.class);

		// then
		assertEquals(screening, foundScreening);
	}

	@Test
	void testGetNonexistentScreening() throws Exception {
		// given
		Long id = Long.MAX_VALUE;

		// when
		mockMvc.perform(get(SCREENINGS_PATH + "/{id}", id))
				// then
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetScreeningsOnDate() throws Exception {
		// given
		LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(30);
		String dateTime = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

		// when
		MvcResult result = mockMvc.perform(get(SCREENINGS_PATH + "/byDate")
				.param("startDate", dateTime))
				.andExpect(status().isOk())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ArrayNode node = (ArrayNode) objectMapper.readTree(json).get("_embedded").get("screeningDtoList");
		List<ScreeningDto> screeningList = objectMapper.readerFor(new TypeReference<List<ScreeningDto>>() {
		}).readValue(node);

		List<ScreeningDto> repoList = screenRepo
				.findAllByTimeBetween(startDateTime, startDateTime.toLocalDate().atTime(23, 59, 59)).stream()
				.map(ScreeningMapper::convertToLimited)
				.sorted(new ScreeningComparator())
				.collect(Collectors.toList());

		// then
		assertEquals(screeningList, repoList);
	}

	@Test
	void testFindByDateWithIncorrectDateFormat() throws Exception {
		// given
		LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(30);
		String dateTime = startDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		// when
		mockMvc.perform(get(SCREENINGS_PATH + "/byDate")
				.param("startDate", dateTime))
				// then
				.andExpect(status().isBadRequest());
	}

}
