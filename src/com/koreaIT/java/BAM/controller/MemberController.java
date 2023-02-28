package com.koreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Member;
import com.koreaIT.java.BAM.util.Util;

public class MemberController extends Controller {

	private List<Member> members;
	private Scanner sc;
	private int lastMemberId;
	
	public MemberController(Scanner sc) {
		this.sc = sc;
		this.members = new ArrayList<>();
		this.lastMemberId = 0;
	}
	
	@Override
	public void doAction(String cmd, String methodName) {
		
		switch(methodName) {
		case "join":
			doJoin();
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ� �Դϴ�");
			break;
		}
	}
	
	private void doJoin() {
		int id = lastMemberId + 1;
		lastMemberId = id;
		String regDate = Util.getDate();
		
		String loginId = null;
		while(true) {
			System.out.printf("�α��� ���̵� : ");
			loginId = sc.nextLine();
			
			if (loginIdDupChk(loginId) == false) {
				System.out.printf("%s��(��) �̹� ������� ���̵��Դϴ�\n", loginId);
				continue;
			}
			
			System.out.printf("%s��(��) ��밡���� ���̵��Դϴ�\n", loginId);
			break;
		}
		
		String loginPw = null;
		String loginPwChk = null;
		while(true) {
			System.out.printf("�α��� ��й�ȣ : ");
			loginPw = sc.nextLine();
			System.out.printf("�α��� ��й�ȣ Ȯ�� : ");
			loginPwChk = sc.nextLine();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("��й�ȣ�� �ٽ� �Է����ּ���");
				continue;
			}
			break;
		}
		System.out.printf("�̸� : ");
		String name = sc.nextLine();
		
		Member member = new Member(id, regDate, loginId, loginPw, name);

		members.add(member);

		System.out.printf("%sȸ���� ȯ���մϴ�\n", loginId);
	}
	
	private boolean loginIdDupChk(String loginId) {
		
		for (Member member : members) {
			if(member.loginId.equals(loginId)) {
				return false;
			}
		}
		
		return true;
	}
}