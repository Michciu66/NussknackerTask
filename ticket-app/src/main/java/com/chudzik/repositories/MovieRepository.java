package com.chudzik.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chudzik.models.Movie;

public interface MovieRepository extends JpaRepository<Movie,Long>{
    
}
