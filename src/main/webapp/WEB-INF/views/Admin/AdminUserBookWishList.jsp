<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
	<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
	<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css" rel="stylesheet" 
	integrity="sha384-T8Gy5hrqNKT+hzMclPo118YTQO6cYprQmhrYwIiQ/3axmI1hQomh7Ud2hPOy8SP1" crossorigin="anonymous">
	
	<title>User - Book Wishlist</title>
	<style>

		@media(min-width:768px) {
			body {
				margin-top: 50px;
			}
			/*html, body, #wrapper, #page-wrapper {height: 100%; overflow: hidden;}*/
		}

		#wrapper {
			padding-left: 0;    
		}

		#page-wrapper {
			width: 100%;        
			padding: 0;
			background-color: #fff;
		}

		@media(min-width:768px) {
			#wrapper {
				padding-left: 225px;
			}

			#page-wrapper {
				padding: 22px 10px;
			}
		}

		/* Top Navigation */

		.top-nav {
			padding: 0 15px;
		}

		.top-nav>li {
			display: inline-block;
			float: left;
		}

		.top-nav>li>a {
			padding-top: 20px;
			padding-bottom: 20px;
			line-height: 20px;
			color: #fff;
		}

		.top-nav>li>a:hover,
		.top-nav>li>a:focus,
		.top-nav>.open>a,
		.top-nav>.open>a:hover,
		.top-nav>.open>a:focus {
			color: #fff;
			background-color: #1a242f;
		}

		.top-nav>.open>.dropdown-menu {
			float: left;
			position: absolute;
			margin-top: 0;
			/*border: 1px solid rgba(0,0,0,.15);*/
			border-top-left-radius: 0;
			border-top-right-radius: 0;
			background-color: #fff;
			-webkit-box-shadow: 0 6px 12px rgba(0,0,0,.175);
			box-shadow: 0 6px 12px rgba(0,0,0,.175);
		}

		.top-nav>.open>.dropdown-menu>li>a {
			white-space: normal;
		}

		/* Side Navigation */

		@media(min-width:768px) {
			.side-nav {
				position: fixed;
				top: 75px;
				left: 225px;
				width: 225px;
				margin-left: -225px;
				border: none;
				border-radius: 0;
				border-top: 1px rgba(0,0,0,.5) solid;
				overflow-y: auto;
				background-color: #222;
				/*background-color: #5A6B7D;*/
				bottom: 0;
				overflow-x: hidden;
				padding-bottom: 0;
			}

			.side-nav>li>a {
				width: 225px;
				border-bottom: 1px rgba(0,0,0,.3) solid;
			}

			.side-nav li a:hover,
			.side-nav li a:focus {
				outline: none;
				background-color: #1a242f !important;
			}
		}

		.side-nav>li>ul {
			padding: 0;
			border-bottom: 1px rgba(0,0,0,.3) solid;
		}

		.side-nav>li>ul>li>a {
			display: block;
			padding: 10px 15px 10px 38px;
			text-decoration: none;
			/*color: #999;*/
			color: #fff;    
		}

		.side-nav>li>ul>li>a:hover {
			color: #fff;
		}

		.navbar .nav > li > a > .label {
		  -webkit-border-radius: 50%;
		  -moz-border-radius: 50%;
		  border-radius: 50%;
		  position: absolute;
		  top: 14px;
		  right: 6px;
		  font-size: 10px;
		  font-weight: normal;
		  min-width: 15px;
		  min-height: 15px;
		  line-height: 1.0em;
		  text-align: center;
		  padding: 2px;
		}

		.navbar .nav > li > a:hover > .label {
		  top: 10px;
		}

		.navbar-brand {
			padding: 5px 15px;
		}

		.navi a {
		    border-bottom: 1px solid #0d172e;
		    border-top: 1px solid #0d172e;
		    color: #ffffff;
		    display: block;
		    font-size: 17px;
		    font-weight: 500;
		    padding: 15px 15px;
		    text-decoration: none;
		}
		
		.navi i {
		    margin-right: 15px;
		    color: #5584ff;
		}
		
		.navi .active a {
		    background: #122143;
		    border-left: 5px solid #5584ff;
		    padding-left: 15px;
		}
		
		.navi a:hover {
		    background: #122143 none repeat scroll 0 0;
		    border-left: 5px solid #5584ff;
		    display: block;
		    padding-left: 15px;
		}

		ul, menu, dir {
			display: block;
			list-style-type: disc;
			margin-block-start: 1em;
			margin-block-end: 1em;
			margin-inline-start: 0px;
			margin-inline-end: 0px;
			padding-inline-start: 10px;
		}

		img {
			width: 10%;
			height: 5%;
			}
		
		.well_top {
		    min-height: 20px;
		    padding: 15px;
		    margin-bottom: 20px;
		    top: 75px;
		    background-color: #f5f5f5;
		    border: 1px solid #e3e3e3;
		    border-radius: 4px;
		    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
		    box-shadow: inset 0 1px 1px rgba(0,0,0,.05);
		}
		
		.btn-mokka { 
		  color: #ffffff; 
		  background-color: #B38744; 
		  border-color: #856820; 
		} 
	</style>
</head>	
<body>
<div id="throbber" style="display:none; min-height:120px;"></div>
<div id="noty-holder"></div>
<div id="wrapper">
    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>
        <!-- Top Menu Items -->
        <ul class="nav navbar-right top-nav">            
            <li>
			        <a href="${contextPath}/edu/auth/logout" class="btn btn-info btn-lg">
			          <span class="glyphicon glyphicon-off"></span>Log Off 
			        </a> 
            </li>
        </ul>
        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
				<!--<li>
					<h2>Library Management System<h2></h2><br>
				</li>-->
				<li>
					<br><br><br><br><br>
						<div class="navi">
						<ul>
							<li><a href="${contextPath}/edu/Admin/dashboard"><i class="fa fa-home" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Home</span></a></li>
							<li class="active"><a href="${contextPath}/edu/Admin/UserManagement"><i class="fa fa-user-plus" aria-hidden="true"></i><span class="hidden-xs hidden-sm">User Managment</span></a></li>
							<li><a href="${contextPath}/edu/Admin/BookManagement"><i class="fa fa-book" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Book Managment</span></a></li>
							
							<!-- <li><a href="#"><i class="fa fa-cog" aria-hidden="true"></i><span class="hidden-xs hidden-sm">Setting</span></a></li>-->
						</ul>
						</div>
				</li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div id="page-wrapper">
        <div class="container-fluid">
            <!-- Page Heading -->
            <form:form>
            <div class="row" id="main" >
            <div class="col-sm-10 col-md-10 well_top" id="content">
			       <div class="form-group col-md-10">
			       <label>User Details</label>
			            <table>
						        <tr>
						            <td>Library Number</td>
						            <td><input type="text" value="${userdetails.getLibRegNum()}" readonly="readonly"></td>
						        </tr>
						        <tr>
						            <td>First Name</td>
						            <td><input type="text" value="${userdetails.getName()}" readonly="readonly"></td>
						        </tr>
						        <tr>
						            <td>Last Name</td>
						            <td><input type="text" value="${userdetails.getLastName()}" readonly="readonly"></td>
						        </tr>
						        <tr>
						            <td>Email Id</td>
						            <td><input type="text" value="${userdetails.getEmail()}" readonly="readonly"></td>
						        </tr>
						    </table>

						</div>
			                       
					</div>
				</div>
			</form:form>
			<br><br>
			<label>Add new book in wishlist</label>
			<br><br><br><br><br><br>
			<form:form>
			<div class="table-responsive table-bordered movie-table">
            	<table class="table movie-table">
			        <thead>
			            <tr>
							<th>ISBN</th>
							<th>Author</th>
							<th>Title</th>
							<th>CallNumber</th>
							<th>Publisher</th>
							<th>YearOfPublication</th>
							<th>Location</th>
							<th>Copies</th>
							<th>CurrentStatus</th>
							<th>Remove</th>
			            </tr>
			        </thead>
			        <tbody>
			        <c:forEach var="wlb" items="${WishlistBooks}">
					 		<tr>
					 			<td><c:out value="${wlb.getIsbn()}"/></td>
								<td><c:out value="${wlb.getAuthor()}"/></td>
								<td><c:out value="${wlb.getTitle()}"/></td>
								<td><c:out value="${wlb.getCallnumber()}"/></td>
								<td><c:out value="${wlb.getPublisher()}"/></td>
								<td><c:out value="${wlb.getYear_of_publication()}"/></td>
								<td><c:out value="${wlb.getLocation()}"/></td>
								<td><c:out value="${wlb.getNum_of_copies()}"/></td>
								<td><c:out value="${wlb.getCurrent_status()}"/></td>
								<td><a href="${contextPath}/edu/User/BookDetails/WishList/user/${userdetails.getId()}/RemoveBook/${wlb.getBookId()}"><span class="glyphicon glyphicon-remove"></span></a><td>		
					 		</tr>
					 	</c:forEach> 
			        </tbody>
		    	</table>
    			
            <!-- /.row -->
        </div>
        </form:form>
        <br><br>
            <form:form action="${contextPath}/edu/User/BookDetails/wishlist/${userdetails.getId()}" method="GET">
            <div class="row" id="main" >
            <div class="col-sm-10 col-md-10 well_top" id="content">
			       <div class="form-group col-md-10">
			            <div class="form-group col-md-6" >
							<label>Search Book</label>
						    <input type="text" name="Searchtext" class="form-control" placeholder="" required="required">
						</div>
						<div class="form-group col-md-6" >
						<table>
						     <tr>
						         <td>Search By ISBN</td>
						         <td><input class="form-check-input" type="radio" name="SearchBy" value="SearchByISBN" required="required"></td>
						     </tr>
						     <tr>
						         <td>Search By Author</td>
						        <td><input class="form-check-input" type="radio" name="SearchBy" value="SearchByAuthor" required="required"></td>
						      </tr>
						      <tr>
						          <td>Search By Title</td>
						          <td><input class="form-check-input" type="radio" name="SearchBy" value="SearchByTitle" required="required"></td>
						      </tr>
						 </table>
						 </div>
							<div class="form-group col-md-3" >
				                <button id="search" type="submit" class="btn btn-primary btn-block" > Search  </button>
							</div>
					</div>
			                       
			</div>
			</div>
			</form:form>
			<label>Add new book in wishlist</label>
			<br><br><br><br><br><br>
			<form:form>
			<div class="table-responsive table-bordered movie-table">
            	<table class="table movie-table">
			        <thead>
			            <tr>
							<th>ISBN</th>
							<th>Author</th>
							<th>Title</th>
							<th>CallNumber</th>
							<th>Publisher</th>
							<th>YearOfPublication</th>
							<th>Location</th>
							<th>Copies</th>
							<th>CurrentStatus</th>
							<th>Add To WishList</th>
			            </tr>
			        </thead>
			        <tbody>
			        <c:forEach var="b" items="${Searchbook}">
					 		<tr>
					 			<td><c:out value="${b.getIsbn()}"/></td>
								<td><c:out value="${b.getAuthor()}"/></td>
								<td><c:out value="${b.getTitle()}"/></td>
								<td><c:out value="${b.getCallnumber()}"/></td>
								<td><c:out value="${b.getPublisher()}"/></td>
								<td><c:out value="${b.getYear_of_publication()}"/></td>
								<td><c:out value="${b.getLocation()}"/></td>
								<td><c:out value="${b.getNum_of_copies()}"/></td>
								<td><c:out value="${b.getCurrent_status()}"/></td>
								<c:choose>
	    							<c:when test="${b.getCurrent_status() == 'Notavailable'}">
								       <td><a href="${contextPath}/edu/User/BookDetails/WishList/user/${userdetails.getId()}/Book/${b.getBookId()}"><span class="glyphicon glyphicon-plus"></span></a><td>
	    							</c:when>    
	    							<c:otherwise>
	        							<td></td>
	    							</c:otherwise>
								</c:choose>			
					 		</tr>
					 	</c:forEach> 
			        </tbody>
		    	</table>
    			
            <!-- /.row -->
        </div>
        </form:form>
        <br><br><br>
        <form:form>
        <!--	<a href="${contextPath}/edu/User/BookDetails/Cart/${userdetails.getId()}/" class="btn btn-info btn-lg">
          <span class="glyphicon glyphicon-shopping-cart"></span> Book Cart ${ItemsinCart}
        </a>-->
        </form:form>
        <!-- /.container-fluid -->
    </div>
    <!-- /#page-wrapper -->
</div><!-- /#wrapper -->
</body>
</html>