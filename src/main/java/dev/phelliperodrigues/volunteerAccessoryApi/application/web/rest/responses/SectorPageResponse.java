package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class SectorPageResponse {
    List<SectorResponse> content;
    CustomPageable pageable;

    public SectorPageResponse(Page<SectorResponse> page) {
        this.content = page.getContent();
        this.pageable = new CustomPageable(
                page.getPageable().getPageNumber(),
                page.getTotalPages(),
                page.getPageable().getPageSize(),
                page.getTotalElements()
        );
    }

    @Data
    class CustomPageable {
        int pageNumber;
        int totalPage;
        int pageSize;
        long totalElements;


        public CustomPageable(int pageNumber, int totalPage, int pageSize, long totalElements) {

            this.pageNumber = pageNumber;
            this.pageSize = pageSize;
            this.totalElements = totalElements;
            this.totalPage = totalPage;
        }
    }
}