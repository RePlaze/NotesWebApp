<%@ include file="common/header.jspf"%>

<title>Login Page</title>
<style>
	body {
		font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen, Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
		background-color: #f7f7f7;
		margin: 0;
	}
	.container{
		margin: 0;
		top: 50px;
		left: 50%;
		position: absolute;
		text-align: center;
		transform: translateX(-50%);
		background-color: #fff;
		border-radius: 12px;
		box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);
		width: 400px;
		height: 500px;
		padding: 20px;
	}

	.box h4 {
		color: #000;
		font-size: 24px;
		font-weight: 600;
		margin-top: 80px;
	}

	.box h4 span {
		color: #8e8e93;
		font-weight: normal;
	}

	.box h5 {
		font-size: 14px;
		color: #8e8e93;
		margin-top: 10px;
		margin-bottom: 50px;
	}

	.box input[type = "text"],.box input[type = "password"] {
		display: block;
		margin: 20px auto;
		background: #f0f0f0;
		border: 0;
		border-radius: 6px;
		padding: 14px 10px;
		width: 320px;
		outline: none;
		color: #000;
		transition: all .2s ease-out;
		font-size: 16px;
		font-weight: 600;
	}

	.box input[type = "text"]:focus,.box input[type = "password"]:focus {
		border: 1px solid #007aff;
	}

	.btn1 {
		border:0;
		background: #007aff;
		color: #fff;
		border-radius: 100px;
		width: 340px;
		height: 49px;
		font-size: 16px;
		position: absolute;
		top: 79%;
		left: 8%;
		transition: 0.3s;
		cursor: pointer;
		font-weight: 600;
	}

	.btn1:hover {
		background: #0062cc;
	}

	.rmb {
		font-size: 13px;
		color: #8e8e93;
		margin-left: -20%;
		margin-top: 10px;
		display: inline-block;
	}

	.forgetpass {
		position: absolute;
		top: 40%;
		right: 5%;
		font-size: 14px;
		color: #007aff;
		text-decoration: none;
		font-weight: 600;
		transition: all .2s ease-out;
		cursor: pointer;
	}


	.dnthave{
		position: absolute;
		top: 92%;
		left: 24%;
	}

	[type=checkbox]:checked + span:before {
		font-family: FontAwesome;
		font-size: 16px;
		content: "\f00c";
		position: absolute;
		top: -4px;
		color: #896cec
	}
</style>
<body id="particles-js">
<div class="animated bounceInDown">
	<div class="container">
		<form method="post" class="box">
			<h4>Login</h4>
			<h5>Sign in to your account.</h5>
			<input type="text" name="name" placeholder="Name">
			<input type="password" name="password" placeholder="Passsword">
			<input type="submit" class="btn1">
		</form>
	</div>

</div>
</body>
</html>
