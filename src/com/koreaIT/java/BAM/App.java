package com.koreaIT.java.BAM;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.java.BAM.dto.Article;
import com.koreaIT.java.BAM.util.Util;

public class App {
	private List<Article> articles;
	
	App(){
		articles = new ArrayList<>();
	}
	
	public void run() {
		System.out.println("== ���α׷� ���� ==");

		makeTestData();
		
		Scanner sc = new Scanner(System.in);

		int lastArticleId = 3;
		
		while (true) {

			System.out.printf("��ɾ�) ");
			String cmd = sc.nextLine().trim();

			if (cmd.length() == 0) {
				System.out.println("��ɾ �Է����ּ���");
				continue;
			}

			if (cmd.equals("exit")) {
				break;
			} else if (cmd.equals("article write")) {
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

			} else if (cmd.startsWith("article list")) {
				
				if (articles.size() == 0) {
					System.out.println("�Խñ��� �����ϴ�");
					continue;
				}
				
				String searchKeyword = cmd.substring("article list".length()).trim();
				
				List<Article> printArticles = new ArrayList<>(articles);
				
				if(searchKeyword.length() > 0) {
					System.out.println("�˻��� : " + searchKeyword);
					
					printArticles.clear();
					
					for(Article article : articles) {
						if(article.title.contains(searchKeyword)) {
							printArticles.add(article);
						}
					}
					if(printArticles.size() == 0) {
						System.out.println("�˻������ �����ϴ�");
						continue;
					}
				}
				
				System.out.println("��ȣ	|	����	|		��¥		|	��ȸ");
				Collections.reverse(printArticles);
				for (Article article : printArticles) {
					System.out.printf("%d	|	%s	|	%s	|	%d\n", article.id, article.title, article.regDate, article.viewCnt);
				}
				
			} else if (cmd.startsWith("article detail ")) {
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				Article foundArticle = getArticleById(id);
				
				if(foundArticle == null) {
					System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
					continue;
				}
				
				foundArticle.addViewCnt();
				
				System.out.printf("��ȣ : %d\n", foundArticle.id);
				System.out.printf("��¥ : %s\n", foundArticle.regDate);
				System.out.printf("���� : %s\n", foundArticle.title);
				System.out.printf("���� : %s\n", foundArticle.body);
				System.out.printf("��ȸ�� : %d\n", foundArticle.viewCnt);
				
			} else if (cmd.startsWith("article modify ")) {
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				Article foundArticle = getArticleById(id);
				
				if(foundArticle == null) {
					System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
					continue;
				}
				
				System.out.printf("������ ���� : ");
				String title = sc.nextLine();
				System.out.printf("������ ���� : ");
				String body = sc.nextLine();
				
				foundArticle.title = title;
				foundArticle.body = body;
				
				System.out.printf("%d������ �����Ǿ����ϴ�\n", id);
				
			} else if (cmd.startsWith("article delete ")) {
				
				String[] cmdBits = cmd.split(" ");
				int id = Integer.parseInt(cmdBits[2]);
				
				Article foundArticle = getArticleById(id);
				
				if(foundArticle == null) {
					System.out.printf("%d�� �Խù��� �������� �ʽ��ϴ�\n", id);
					continue;
				}
				
				articles.remove(articles.indexOf(foundArticle));
				
				System.out.printf("%d�� �Խñ��� �����߽��ϴ�\n", id);
				
			} else {
				System.out.println("�������� �ʴ� ��ɾ� �Դϴ�");
			}
		}

		System.out.println("== ���α׷� �� ==");

		sc.close();

	}

	private Article getArticleById(int id) {
		
		for(Article article : articles) {
			if(article.id == id) {
				return article;
			}
		}
		
		return null;
	}

	private void makeTestData() {
		System.out.println("�Խù� �׽�Ʈ �����͸� �����մϴ�");
		articles.add(new Article(1, Util.getDate(), "����1", "����1", 10));
		articles.add(new Article(2, Util.getDate(), "����2", "����2", 20));
		articles.add(new Article(3, Util.getDate(), "����3", "����3", 30));
	}
}