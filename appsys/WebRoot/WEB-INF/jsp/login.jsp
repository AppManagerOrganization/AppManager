<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- Meta, title, CSS, favicons, etc. -->
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>首页</title>

<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css"
	rel="stylesheet">
<!-- Font Awesome -->
<link
	href="${pageContext.request.contextPath }/statics/css/font-awesome.min.css"
	rel="stylesheet">
<!-- NProgress -->
<link
	href="${pageContext.request.contextPath }/statics/css/nprogress.css"
	rel="stylesheet">
<!-- Animate.css -->
<link href="https://colorlib.com/polygon/gentelella/css/animate.min.css"
	rel="stylesheet">

<!-- Custom Theme Style -->
<link
	href="${pageContext.request.contextPath }/statics/css/custom.min.css"
	rel="stylesheet">
</head>

<body class="login">
	<div>
		<a class="hiddenanchor" id="signup"></a> <a class="hiddenanchor"
			id="signin"></a>

		<div class="login_wrapper">
			<div class="animate form login_form">
				<section class="login_content">
					<form>
						<h1>APP信息管理平台</h1>
						<h3>
							<a style="color:red" href="manager/login.html">后台管理系统入口</a>
						</h3>
						<h3>
							<a style="color:blue" href="dev/login.html">开发者平台入口</a>
						</h3>
						<div class="clearfix"></div>
						<div class="separator">
							<div>
								<p>?2016 All Rights Reserved.</p>
							</div>
						</div>
					</form>
				</section>
			</div>
		</div>
	</div>
</body>
</html>

