package com.grow_site.grow_site.DTO.admin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberStateDTO {

    private Long totalMemberCount;
    private Long todaySignMemberCount;

    private Long adminMemberCount;
    private Long memberCount;
    private Long guestMemberCount;
}
