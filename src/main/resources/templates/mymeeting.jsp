<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://fonts.googleapis.com/css?family=Merriweather+Sans:400,700"
	rel="stylesheet" />
<link
	href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic"
	rel="stylesheet" type="text/css" />
<!-- SimpleLightbox plugin CSS-->
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/SimpleLightbox/2.1.0/simpleLightbox.min.css"
	rel="stylesheet" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css2/styles.css" rel="stylesheet" />
<title>빠르고 간편한 모임</title>
</head>
<body>
	<br>
	<br>
	
	<table class="table" style="margin-left:auto; 
    margin-right:auto;top:17%; width:60%;">
			<tr>
				<th><h4 style="color:#f4623a;">모임이름</h4></th>
				<th><h4 style="color:#f4623a;">모임일정</h4></th>
				<th><h4 style="color:#f4623a;">모임장소</h4></th>
				<th><h4 style="color:#f4623a;">모임취소</h4></th>
			</tr>
			<tr>
				<th>술술</th>
				<th>2022.07.22</th>
				<th>미정</th>
				<th>제거하기</th>
			</tr>
			<tr>
				<th onClick="location.href='http://www.daum.net/'">${meeting.name}</th>
				<th>${meeting.date}</th>
				<th>${meeting.place}</th>
				<th onClick="location.href='http://www.daum.net/'"><div class="xmark xmark2"></div></th>
			</tr>
	</table>
</body>
</html>

