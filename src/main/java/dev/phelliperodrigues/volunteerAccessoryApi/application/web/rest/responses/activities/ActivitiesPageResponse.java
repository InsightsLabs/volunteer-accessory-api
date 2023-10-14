package dev.phelliperodrigues.volunteerAccessoryApi.application.web.rest.responses.activities;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivitiesPageResponse {
    List<ActivityResponse> content;
    CustomPageable pageable;

    public ActivitiesPageResponse(Page<ActivityResponse> page) {
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