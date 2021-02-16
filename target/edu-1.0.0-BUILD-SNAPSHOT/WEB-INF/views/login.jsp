<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<title>Login Page</title>
	<style>
			body {
		    font-family: "Lato", sans-serif;
		}

		.main-head{
		    height: 150px;
		    background: #FFF;
		   
		}

		.sidenav {
		    height: 100%;
		    background-color: #000;
		    overflow-x: hidden;
		    padding-top: 20px;
		}
		
		.main {
		    padding: 0px 10px;
		}
		
		@media screen and (max-height: 450px) {
		    .sidenav {padding-top: 15px;}
		}
		
		@media screen and (max-width: 450px) {
		    .login-form{
		        margin-top: 10%;
		    }
		
		    .register-form{
		        margin-top: 10%;
		    }
		}
		
		@media screen and (min-width: 768px){
		    .main{
		        margin-left: 40%; 
		    }
		
		    .sidenav{
		        width: 20%;
		        position: fixed;
		        z-index: 1;
		        top: 0;
		        left: 0;
		    }
		
		    .login-form{
		        margin-top: 80%;
		    }
		
		    .register-form{
		        margin-top: 20%;
		    }
		}
		
		
		.login-main-text{
		    margin-top: 20%;
		    padding: 60px;
		    color: #fff;
		}
		
		.login-main-text h2{
		    font-weight: 300;
		}
		
		.btn-black{
		    background-color: #000 !important;
		    color: #fff;
		}
	
	</style>
</head>	
<body>
	<div class="sidenav">
         <div class="login-main-text">
            <h2>Library Management System<h2></h2><br><br><br><br><h4>Login Page</h4>
            <p>Login or register from here to access.</p>
         </div>
      </div>
      <div class="main">
         <div class="col-md-6 col-sm-12">
            <div class="login-form">
               <form action="validate" method="post">
               	  <div class="form-group">
               		<span>${message}</span>
               	  </div>
                  <div class="form-group">
                     <label>User Name</label>
                     <input type="text" class="form-control" placeholder="User Name" id="username" name="username" required="required"/>
                  </div>
                  <div class="form-group">
                     <label>Password</label>
                     <input type="password" class="form-control" placeholder="Password" id="password" name="password" required="required"/>
                  </div>
                  <div class="form-group">
                  	  <font color="red"><label>${error}</label></font>
                  </div>
                  <button type="submit" class="btn btn-black" value="Login" name="option">Login</button>
               </form>
               <br>
               <form action="register" method="get">
               	   <button type="submit" class="btn btn-secondary">Register</button>
               </form>
            </div>
         </div>
      </div>
</body>
</html>