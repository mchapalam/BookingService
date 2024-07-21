package com.example.lastProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationInfo {
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

}
