//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
package com.spring.bearbom.controller.mypage;

import com.spring.bearbom.dto.CourseDTO;
import com.spring.bearbom.dto.InquiryDTO;
import com.spring.bearbom.dto.ResponseDTO;
import com.spring.bearbom.dto.UserDTO;
import com.spring.bearbom.entity.User;
import com.spring.bearbom.jwt.JwtTokenProvider;
import com.spring.bearbom.service.mypage.MypageService;
import com.spring.bearbom.service.test.TestService;
import com.spring.bearbom.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
@Slf4j
@RequiredArgsConstructor
public class MypageController {
	@Autowired
	private UserService userService;

	@Autowired
	private MypageService mypageService;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;


	private final TestService testService;

	@GetMapping("/getUser")
	public ResponseEntity<?> mypage(User user, @AuthenticationPrincipal String userId) {
		System.out.println(userId);
		try {
//		System.out.println(passwordEncoder.getClass());
//		user.setUserPw(passwordEncoder.encode(user.getUserPw()));
//		System.out.println(user.getUserPw());
			//회원 가입 후 가입된 회원 정보 받아오는 객체 생성
			User getUser = mypageService.getUser(userId);
//		User loginUser = userRepository.findByUserId(userId);

			//리액트로 리턴해 줄 MemberDTO 객체 생성
			UserDTO userDTO = new UserDTO();

			//member는 리스트로 리턴되는 게 아니여서 responseBody에 memberDTO를 담아서 리턴
			userDTO.setUserId(getUser.getUserId());
			userDTO.setUserPw(getUser.getUserPw());
			userDTO.setRole(getUser.getRole());
			userDTO.setUserEmail(getUser.getUserEmail()); //
			userDTO.setUserNm(getUser.getUserNm());
			userDTO.setUserNickName(getUser.getUserNickName());
			userDTO.setUserAddress(getUser.getUserAddress());
			userDTO.setUserAddressDef(getUser.getUserAddressDef());
			userDTO.setUserTel(getUser.getUserTel());

			userDTO.setUserYn(getUser.getUserYn());



			System.out.println("///////////////"+ userDTO);
			return ResponseEntity.ok().body(userDTO);
		} catch(Exception e) {
			ResponseDTO<UserDTO> response = new ResponseDTO<>();

			response.setError(e.getMessage());
			return ResponseEntity.badRequest().body(response);
		}

	}
	// 유저 정보 변경
	@PostMapping("/updateUserInfo")
	public ResponseEntity<?> updateUserInfo(@RequestBody User user, @AuthenticationPrincipal String userId){
		// 등록된 사용자 정보를 조회한다 
//		User oldUser = mypageService.getUser(user.getUserId());
//
//		// 화면 input 항목에서 받아온 값들을 변경한다 
//		oldUser.setUserPw(user.getUserPw());
//		oldUser.setUserNm(user.getUserNm());
//		oldUser.setUserNickName(user.getUserNickName());
//		oldUser.setUserTel(user.getUserTel());
//		oldUser.setUserAddress(user.getUserAddress());
//		oldUser.setUserAddressDef(user.getUserAddressDef());
//		oldUser.setUserZipcode(user.getUserZipcode());
//		oldUser.setUserEmail(user.getUserEmail());
//		oldUser.setUserYn(user.getUserYn());
		System.out.println("userId : "+user.getUserId());
//		user.setUserId(userId);	

		mypageService.updateUser(user);

		User newUser = mypageService.getUser(user.getUserId());

		UserDTO userDTO = new UserDTO();

		// 실제 DB 저장 
//		userService.updateUser(oldUser);


		return ResponseEntity.ok().body("success");
	}



//
//		return ResponseEntity.ok().body("success");
//	}

	//y 인것만 화면에 뿌려주는거 맵퍼를이용한
	@GetMapping("/getInquiryReference")
	public Map<String, Object> getInquiryReference(InquiryDTO inquiryDTO, @AuthenticationPrincipal String userId){
		try {
			inquiryDTO.setUserId(userId);
			List<Map<String, Object>> getInquiryReference1 = mypageService.getInquiryReference(inquiryDTO);
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("getInquiryReference1", getInquiryReference1);

			return resultMap;
		} catch(Exception e) {
			Map<String,Object> error = new HashMap<String,Object>();
			error.put("error", e.getMessage());
			return error;
		}
	}
	//y를 n으로 바꾸는 update
	@PostMapping("/updateInquiryReference")
	public void updateInquiryReference(@RequestBody InquiryDTO inquiryDTO, @AuthenticationPrincipal String userId){
		inquiryDTO.setUserId(userId);
		System.out.println("before inquiryDTO : " +inquiryDTO);
		log.info("inquiryDTO : {}", inquiryDTO);
		mypageService.updateInquiryReference(inquiryDTO);
		System.out.println("after inquiryDTO : " +inquiryDTO);

	}


	//마이페이지
	// 수강 중인 강좌(takingCourseCnt), 수강 완료 강좌(takenCourseCnt),
	// 개설한 강좌(openedCourseCnt), 찜한 클래스(likedCourseCnt) 수량 컨트롤러
	@GetMapping("/getMyPageCnt")
	public Map<String, Object> getMyPageCnt(@AuthenticationPrincipal String userId) {
		// 수강 중인 강좌를 위한 현재 날짜
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		System.out.println(date.format(formatter));
		try{
			CourseDTO courseDTO = new CourseDTO();
			courseDTO.setUserId(userId);
			courseDTO.setTakingCourseDate(date.format(formatter));

			System.out.println("courseDTO : /////" +courseDTO);
			List<Map<String, Object>> likedCourseCnt = testService.likedCourseCnt(courseDTO);
			List<Map<String, Object>> takingCourseCnt = testService.takingCourseCnt(courseDTO);
			List<Map<String, Object>> takenCourseCnt = testService.takenCourseCnt(courseDTO);
			List<Map<String, Object>> openedCourseCnt = testService.openedCourseCnt(courseDTO);

			Map<String, Object> resultMap = new HashMap<>();
			resultMap.put("likedCourseCnt", likedCourseCnt);
			resultMap.put("takingCourseCnt",takingCourseCnt);
			resultMap.put("takenCourseCnt", takenCourseCnt);
			resultMap.put("openedCourseCnt", openedCourseCnt);
			return resultMap;

		}catch (Exception e) {
			Map<String, Object> errorMap = new HashMap<>();
			errorMap.put("error", e.getMessage());
			return errorMap;
		}
	}
}
