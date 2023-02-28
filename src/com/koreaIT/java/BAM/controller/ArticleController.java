package com.koreaIT.java.BAM.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.util.Util;

public class ArticleController extends Controller {

	private List<Article> articles;
	private Scanner sc;
	private int lastArticleId;
	private String cmd;
	
	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articles = new ArrayList<>();
		this.lastArticleId = 3;
	}

	@Override
	public void doAction(String cmd, String methodName) {
		this.cmd = cmd;
		
		switch(methodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("�������� �ʴ� ��ɾ� �Դϴ�");
			break;
		}
	}
	
	private void doWrite() {
		int id = lastArticleId + 1;
		lastArticleId = id;
		String regDate = Util.getDate();
		System.out.printf("���� : ");
		String title = sc.nextLine();
		System.out.printf("���� : ");
		String body = sc.nextLine();

		Article article = new Article(id, regDate, title, body);

		articles.add(article);

		System.out.printf("%d�� ���� �����Ǿ����ϴ�\n", id);
	}

	private void showList() {
		if (articles.size() == 0) {
			System.out.println("�Խñ��� �����ϴ�");
			return; // -> �������� �Լ��� �����Ű�� �Ѱ��ִ� ���� ����.
		}

		String searchKeyword = cmd.substring("article list".length()).trim();

		List<Article> printArticles = new ArrayList<>(articles);

		if (searchKeyword.length() > 0) {
			System.out.println("�˻��� : " + searchKeyword);

			printArticles.clear();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					printArticles.add(article);
				}
			}
			if (printArticles.size() == 0) {
				System.out.println("�˻������ �����ϴ�");
				return;
			}
		}

		System.out.println("��ȣ	|	����	|		��¥		|	��ȸ");
		Collections.reverse(printArticles);
		for (Article article : printArticles) {
			System.out.printf("%d	|	%s	|	%s	|	%d\n", article.id, article.title, article.regDate,
					article.viewCnt);
		}
	}

	private void showDetail() {
		String[] cmdBits = cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("��ɾ Ȯ�����ּ���");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
			return;
		}

		foundArticle.addViewCnt();

		System.out.printf("��ȣ : %d\n", foundArticle.id);
		System.out.printf("��¥ : %s\n", foundArticle.regDate);
		System.out.printf("���� : %s\n", foundArticle.title);
		System.out.printf("���� : %s\n", foundArticle.body);
		System.out.printf("��ȸ�� : %d\n", foundArticle.viewCnt);
	}
	
	private void doModify() {
		String[] cmdBits = cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("��ɾ Ȯ�����ּ���");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
			return;
		}

		System.out.printf("������ ���� : ");
		String title = sc.nextLine();
		System.out.printf("������ ���� : ");
		String body = sc.nextLine();

		foundArticle.title = title;
		foundArticle.body = body;

		System.out.printf("%d������ �����Ǿ����ϴ�\n", id);
	}

	private void doDelete() {
		String[] cmdBits = cmd.split(" ");
		
		if (cmdBits.length == 2) {
			System.out.println("��ɾ Ȯ�����ּ���");
			return;
		}
		
		int id = Integer.parseInt(cmdBits[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
			return;
		}

		articles.remove(articles.indexOf(foundArticle));

		System.out.printf("%d�� �Խñ��� �����߽��ϴ�\n", id);
	}
	
	private Article getArticleById(int id) {

		for (Article article : articles) {
			if (article.id == id) {
				return article;
			}
		}

		return null;
	}
	
	public void makeTestData() {
		System.out.println("�Խù� �׽�Ʈ �����͸� �����մϴ�");
		articles.add(new Article(1, Util.getDate(), "����1", "����1", 10));
		articles.add(new Article(2, Util.getDate(), "����2", "����2", 20));
		articles.add(new Article(3, Util.getDate(), "����3", "����3", 30));
	}
}}