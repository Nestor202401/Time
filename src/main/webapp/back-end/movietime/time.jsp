<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Bootstrap Dashboard</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="noindex">
    <!-- Google fonts - Roboto -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700">
    <!-- Choices CSS-->
    <link rel="stylesheet" href="../resources/vendor/choices.js/public/assets/styles/choices.min.css">
    <!-- Custom Scrollbar-->
    <link rel="stylesheet" href="../resources/vendor/overlayscrollbars/css/OverlayScrollbars.min.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="../resources/css/style.default.premium.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="../resources/css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="img/favicon.ico">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
  <style >
  
  </style>
  </head>
  <body>
    <!-- Side Navbar -->
    <nav class="side-navbar">
      <div class="side-navbar-inner">
        <!-- Sidebar Header    -->
        <div class="sidebar-header d-flex align-items-center justify-content-center p-3 mb-3">
          <!-- User Info-->
          <div class="sidenav-header-inner text-center">
  <a href="pages-profile.html">
    <img class="img-fluid rounded-circle avatar mb-2" src="../resources/img/avatar-7.jpg" alt="person">
  </a>
  <h2 class="h5 text-white text-uppercase mb-0" style="font-size: 1em; font-weight: bold;">Time影城後台管理</h2>
  <p class="text-sm mb-0 text-muted" style="font-size: 0.75em; font-weight: bold;"></p>
</div>
          <!-- Small Brand information, appears on minimized sidebar--><a class="brand-small text-center" href="index.html">
            <p class="h1 m-0">BD</p></a>
        </div>
        <!-- Sidebar Navigation Menus--><span class="text-uppercase text-gray-500 text-sm fw-bold letter-spacing-0 mx-lg-2 heading"></span>
       <ul class="list-unstyled">                  
         
          <li class="sidebar-item active"><a class="sidebar-link" href="#formsDropdown" aria-expanded="aria-expanded" data-bs-toggle="collapse"> 
              <svg class="svg-icon svg-icon-sm svg-icon-heavy me-2">
                <use xlink:href="#survey-1"> </use>
              </svg>電影相關設定 </a>
            <ul class="collapse list-unstyled show" id="formsDropdown">
              <li><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/movie/movie.jsp">電影設定</a></li>
              <li><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/movieimg/img.jsp">圖片管理</a></li>
              <li class="active"><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/movietime/time.jsp">場次管理</a></li>
              <li><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/cinema/cinema.jsp">影廳管理</a></li>
            </ul>
          </li>
          
          <li class="sidebar-item"><a class="sidebar-link" href="#chartsDropdown" data-bs-toggle="collapse"> 
              <svg class="svg-icon svg-icon-sm svg-icon-heavy me-2">
                <use xlink:href="#sales-up-1"> </use>
              </svg>票務相關設定 </a>
            <ul class="collapse list-unstyled " id="chartsDropdown">
              <li><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/ticorder/main_ticorder.jsp">訂單管理</a></li>
              <li><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/tictypes/main_tictypes.jsp">票種設定</a></li>
            </ul>
          </li>
          
          <li class="sidebar-item"><a class="sidebar-link" href=""> 
              <svg class="svg-icon svg-icon-sm svg-icon-heavy me-2">
                <use xlink:href="#real-estate-1"> </use>
              </svg>周邊商品</a></li>
          
          <li class="sidebar-item"><a class="sidebar-link" href="<%=request.getContextPath()%>/back-end/reports/report.jsp"> 
              <svg class="svg-icon svg-icon-sm svg-icon-heavy me-2">
                <use xlink:href="#real-estate-1"> </use>
              </svg>討論區管理</a></li>
          
          </ul>  
      </div>
    </nav>
    <div class="page">
      <!-- navbar-->
      <header class="header mb-5 pb-3">
        <nav class="nav navbar fixed-top">
          <div class="container-fluid">
            <div class="d-flex align-items-center justify-content-between w-100">
              <div class="d-flex align-items-center">
                  <svg class="svg-icon svg-icon-sm svg-icon-heavy text-white">
                    <use xlink:href="#menu-1"> </use>
                  </svg></a><a class="navbar-brand ms-2" href="index.html">
                  <div class="brand-text d-none d-md-inline-block text-uppercase letter-spacing-0"><span class="text-white fw-normal text-xs"></span><strong class="text-primary text-sm"></strong></div></a></div>
              <ul class="nav-menu mb-0 list-unstyled d-flex flex-md-row align-items-md-center">
                <!-- Notifications dropdown-->
                
                <!-- Messages dropdown-->
                
                <!-- Languages dropdown    -->
                <li class="nav-item dropdown">
                  <ul class="dropdown-menu dropdown-menu-end mt-sm-3 shadow-sm" aria-labelledby="languages">
                  </ul>
                </li>
                <!-- Log out-->
                <li class="nav-item"><a class="nav-link text-white text-sm ps-0" href="login.html"> <span class="d-none d-sm-inline-block">Logout</span>
                    <svg class="svg-icon svg-icon-xs svg-icon-heavy">
                      <use xlink:href="#security-1"> </use>
                    </svg></a></li>
              </ul>
            </div>
          </div>
        </nav>
      </header>
      <!-- Breadcrumb-->
      <div class="bg-gray-200 text-sm">
        <div class="container-fluid">
          <nav aria-label="breadcrumb">
            <ol class="breadcrumb mb-0 py-3">
              <li class="breadcrumb-item"><a class="fw-light" href="">Home</a></li>
              <li class="breadcrumb-item active fw-light" aria-current="page">showtime  </li>
            </ol>
          </nav>
        </div>
      </div>
      <!-- Page Header-->
     
      <!-- Forms Section-->
      <section class="pb-5"> 
        <div class="container-fluid">
          <div class="row">
<!------------------------------------- 內容放這裡 ---------------------------------------------->

<jsp:include page="keyword.jsp" />
            



<!------------------------------------- 底到這放這裡 ---------------------------------------------->
          </div>
        </div>
      </section>
  <!-- ----------------------------------------------------------------------------------------------------------------------------------------- -->
      <footer class="main-footer w-100 position-absolute bottom-0 start-0 py-2" style="background: #222">
        <div class="container-fluid">
          <div class="row text-center gy-3">
            <div class="col-sm-6 text-sm-start">
              <p class="mb-0 text-sm text-gray-600">Your company &copy; 2017-2023</p>
            </div>
            <div class="col-sm-6 text-sm-end">
              <p class="mb-0 text-sm text-gray-600">Version 2.1.1</p>
            </div>
          </div>
        </div>
      </footer>
    </div>
    <div class="collapse" id="style-switch">
      <h5 class="mb-3">Select theme colour</h5>
      <form class="mb-3">
        <select class="form-select" name="colour" id="colour">
          <option value="">select colour variant</option>
          <option value="../resourcescss/style.default.premium.css">green</option>
          <option value="../resourcescss/style.red.premium.css">red</option>
          <option value="../resourcescss/style.violet.premium.css">violet</option>
          <option value="../resourcescss/style.pink.premium.css">pink</option>
          <option value="../resourcescss/style.sea.premium.css">sea</option>
          <option value="../resourcescss/style.blue.premium.css">blue</option>
        </select>
      </form>
      <p><img class="img-fluid" src="img/template-mac.png" alt=""></p>
      <p class="text-muted text-xs">Stylesheet switching is done via JavaScript and can cause a blink while page loads. This will not happen in your production code.</p>
    </div>
    <!-- JavaScript files-->
    <script src="../resources/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
    <script src="../resources/vendor/chart.js/Chart.min.js"></script>
    <script src="../resources/vendor/just-validate/js/just-validate.min.js"></script>
    <script src="../resources/vendor/choices.js/public/assets/scripts/choices.min.js"></script>
    <script src="../resources/vendor/overlayscrollbars/js/OverlayScrollbars.min.js"></script>
    <script src="../resources/js/demo.js"> </script>
    <!-- Main File-->
    <script src="js/front.js"></script>
    <script>
      // ------------------------------------------------------- //
      //   Inject SVG Sprite - 
      //   see more here 
      //   https://css-tricks.com/ajaxing-svg-sprite/
      // ------------------------------------------------------ //
      function injectSvgSprite(path) {
      
          var ajax = new XMLHttpRequest();
          ajax.open("GET", path, true);
          ajax.send();
          ajax.onload = function(e) {
          var div = document.createElement("div");
          div.className = 'd-none';
          div.innerHTML = ajax.responseText;
          document.body.insertBefore(div, document.body.childNodes[0]);
          }
      }
      // this is set to BootstrapTemple website as you cannot 
      // inject local SVG sprite (using only 'icons/orion-svg-sprite.svg' path)
      // while using file:// protocol
      // pls don't forget to change to your domain :)
      injectSvgSprite('icons/orion-svg-sprite.svg'); 
      
      
    </script>
    <!-- FontAwesome CSS - loading as last, so it doesn't block rendering-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css" integrity="sha384-fnmOCqbTlWIlj8LyTjo7mOUStjsKC4pOpQbqyi7RrhN7udi9RwhKkMHpvLbHG9Sr" crossorigin="anonymous">
  </body>
</html>