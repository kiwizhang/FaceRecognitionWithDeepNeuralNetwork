<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>Carnegie Financial Service</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
    <script src="js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!-- Custom styles for this template -->
    <link href="css/carousel.css" rel="stylesheet">
  </head>
<!-- NAVBAR
================================================== -->
  <body>
    <div class="navbar-wrapper">
      <div class="container">

        <nav class="navbar navbar-inverse navbar-static-top" role="navigation">
          <div class="container">
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#">Carnegie Financial Services</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
              <ul class="nav navbar-nav">
                <li class="active"><a href="#">Home</a></li>
                <!-- <li><a href="#about">About</a></li>
                <li><a href="#contact">Contact</a></li> -->
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false"> Contact <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="#">412-111-1111</a></li>
                    <li><a href="#">5000 Forbes Avenue,</a></li>
                    <li><a href="#">Pittsburgh, PA 15213</a></li>
                    <!-- <li class="divider"></li>
                    <li class="dropdown-header">Nav header</li>
                    <li><a href="#">Separated link</a></li>
                    <li><a href="#">One more separated link</a></li> -->
                  </ul>
                </li>
              </ul>
            </div>
          </div>
        </nav>

      </div>
    </div>


    <!-- Carousel
    ================================================== -->
    <div id="myCarousel" class="carousel slide" data-ride="carousel">
      <!-- Indicators -->
      <ol class="carousel-indicators">
        <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
        <li data-target="#myCarousel" data-slide-to="1"></li>
        <li data-target="#myCarousel" data-slide-to="2"></li>
      </ol>
      <div class="carousel-inner" role="listbox">
        <div class="item active">
          <img src="img/homepage1.jpg" alt="First slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Carnegie Financial Services</h1>
              <p>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br>&nbsp;<br></p>
              <!-- <p><a class="btn btn-lg btn-primary" href="#" role="button">Start</a></p> -->
            </div>
          </div>
        </div>
        <div class="item">
          <img src="img/homepage3.jpg" alt="Second slide">
          <div class="container">
            <div class="carousel-caption">
              <h1>Your Personal Fund Manager</h1>
            </div>
          </div>
        </div>
        <div class="item">
          <img src="img/homepage2.jpg" alt="Third slide">
          <div class="container">
            <div class="carousel-caption">
            <h1>One more for good measure</h1>
            <p>&nbsp;<br>&nbsp;<br>&nbsp;</p>
              <!-- <p><a class="btn btn-lg btn-primary" href="#" role="button">Browse</a></p> -->
            </div>
          </div>
        </div>
      </div>
      <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
        <span class="sr-only">Previous</span>
      </a>
      <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
        <span class="sr-only">Next</span>
      </a>
    </div><!-- /.carousel -->


    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing">

      <!-- Three columns of text below the carousel -->
      <div class="row">
	    <div class="col-lg-2">
	    &nbsp;
	    </div>
        <div class="col-lg-4">
          <img class="img-circle" src="img/customer.png" alt="Generic placeholder image" style="width: 140px; height: 140px;">
          <h2>Customer Entry</h2>
          <p>This is the entry for customers to manage <br> personal funds and research funds. </p>
          <p><a class="btn btn-primary" type="submit" href="loginCustomer.do">Customer Login</a></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-lg-4">
          <img class="img-circle" src="img/employee.png" alt="Generic placeholder image" style="width: 140px; height: 140px;">
          <h2>Employee Entry</h2>
          <p>This is the entry for employees to manage <br> all funds and customers' accounts.</p>
          <p><a class="btn btn-primary" type="submit" href="loginEmployee.do">Employee Login</a></p>
        </div><!-- /.col-lg-4 -->
        <div class="col-lg-2">
	    &nbsp;
	    </div>
      </div><!-- /.row -->

      <!-- FOOTER -->
      <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>&copy; Carnegie Financial Services 2015 &middot; Team 13 &middot; Bazinga </p>
      </footer>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/docs.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
