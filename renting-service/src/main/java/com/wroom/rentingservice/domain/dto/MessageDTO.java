package com.wroom.rentingservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MessageDTO {

	private Long toUserId;
	private Long rentRequestId;
	private String title;
	private String content;
	private Long fromUserId;
	
	
}
