MyRoutine
프로젝트 소개

MyRoutine은 사용자가 자신의 일상 루틴을 등록하고 관리할 수 있는 웹 애플리케이션입니다.
Spring Boot와 JPA를 활용하여 개발하였으며, 회원가입 및 로그인 기능을 통해 사용자별로 루틴을 관리할 수 있습니다.

개발 기간
2026.06

기술 스택
Backend
Java
Spring Boot
Spring Data JPA

Database
MariaDB

Frontend
Thymeleaf
HTML
CSS

Version Control
Git
GitHub

주요 기능
회원 기능
회원가입
로그인
로그아웃
세션 기반 사용자 인증

루틴 기능
루틴 등록
루틴 수정
루틴 삭제
루틴 완료 처리
루틴 검색
완료/미완료 필터
오늘 루틴 조회
완료율 확인

사용자 데이터 분리
로그인한 사용자만 자신의 루틴 조회 가능
다른 사용자의 루틴 수정/삭제 방지

ERD
User
id
username
password
Routine
id
title
description
date
done
user_id

프로젝트를 통해 배운 점
Spring Boot 기반 CRUD 개발
JPA Entity 연관관계
MariaDB 연동
세션 기반 로그인 처리
Git/GitHub 버전 관리
사용자 권한 검증
