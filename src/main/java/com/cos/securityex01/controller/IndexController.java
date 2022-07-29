package com.cos.securityex01.controller;

import com.cos.securityex01.config.auth.PrincipalDetails;
import com.cos.securityex01.model.User;
import com.cos.securityex01.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;

@Controller // view를 리턴하겠다. 만약 RestController 이면 view 리턴 x
@RequiredArgsConstructor
public class IndexController {

//	@Autowired
	private final UserRepository userRepository;

//	@Autowired
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping({ "", "/" })
	public @ResponseBody String index() {

		// 머스테치 기본 폴더 : src/main/resources/
		// 뷰리졸버 설정 : templates (prefix), .mustache (suffix) ,
		return "인덱스 페이지입니다.";
	}

	/**
	 * '/user' 요청 시 해당 사용자 인증 절차 API
	 * @param principal
	 * @return
	 */
	@GetMapping("/user")
	public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principal) {

		System.out.println("Principal : " + principal);

		// 파라미터로 받은 principal 를 iterator 로 순차 출력 해보기
		Iterator<? extends GrantedAuthority> iter = principal.getAuthorities().iterator();

		while (iter.hasNext()) {
			GrantedAuthority auth = iter.next();
			System.out.println(auth.getAuthority());
		}

		return "유저 페이지입니다."; // @ResponseBody 이니 리턴 문자가 httpResponseBody에 그대로 뿌려짐
	}

	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "어드민 페이지입니다.";
	}
	
	//@PostAuthorize("hasRole('ROLE_MANAGER')")
	//@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Secured("ROLE_MANAGER")
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "매니저 페이지입니다.";
	}

	@GetMapping("/login")
	public String login() {
		return "login"; // login 페이지 리턴
	}

	@GetMapping("/join")
	public String join() {
		return "join"; // join 페이지 리턴
	}

	@PostMapping("/joinProc")
	public String joinProc(User user) {

		System.out.println("회원가입 진행 : " + user);

		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);

		user.setPassword(encPassword);
		user.setRole("ROLE_USER");

		userRepository.save(user); // persist

		return "redirect:/";
	}
}
