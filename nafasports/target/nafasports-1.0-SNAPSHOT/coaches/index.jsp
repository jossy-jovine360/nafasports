<%-- 
    Document   : index
    Created on : 11 Oct 2022, 1:53:08 PM
    Author     : josiah
--%>

<%@page import="appData.AppData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%  
            session.removeAttribute("username");
            
            String portal = (String)request.getAttribute("portal");
            if(portal == null){
                portal = "";
            }else{
                portal = "coaches/";
            }
            if(portal.equals("coaches/coaches/")){
                portal = "coaches/";
            }
            
            String domain = AppData.DOMAIN_COACH;
            String msg = (String)request.getAttribute("msg");
            String disp = "hidden";
            if(msg == null){
                msg = "";
            }else{
                disp = "show";
            }
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome to NAFA Coaches Login Page </title>
        <meta charset="UTF-8">
        <meta name="description" content="NAFA is created to promote and enhance growth across the 36 states of the federation, including the FCT, through 
                            contact and noncontact American Football">
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <!-- ============================== favicon script section =============================== -->
        <link rel="apple-touch-icon" sizes="180x180" href="favs/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="favs/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="favs/favicon-16x16.png">
        <link rel="manifest" href="favs/site.webmanifest">
        <link rel="mask-icon" href="favs/safari-pinned-tab.svg" color="#5bbad5">
        <link rel="shortcut icon" href="favs/favicon.ico">
        <meta name="msapplication-TileColor" content="#da532c">
        <meta name="msapplication-config" content="favs/browserconfig.xml">
        <meta name="theme-color" content="#ffffff">
        <!-- ============================== favicon script section =============================== -->

        <link rel="stylesheet" type="text/css" href="css/bootstrap.css">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="css/margin.css">
        <link rel="stylesheet" type="text/css" href="css/padding.css">
        <link rel="stylesheet" type="text/css" href="css/text-size.css?v=1">
        <link rel="stylesheet" type="text/css" href="css/style.css?v=1">
        <link rel="stylesheet" type="text/css" href="css/dimension.css">
        <link rel="stylesheet" type="text/css" href="css/border_radius.css">
        <link rel="stylesheet" type="text/css" href="css/form.css">
        <link rel="stylesheet" type="text/css" href="css/tooltip.css">
        <link rel="stylesheet" type="text/css" href="css/fontawesome-6.1.1/css/all.min.css"/>
        <script type="text/javascript" src="js/datetime.js"></script>
        <script type="text/javascript" src="js/session.js"></script>
        <script type="text/javascript" src="js/form_validator_ims.js"></script>
        <script type="text/javascript" src="js/alerts.js"></script>
        <script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
        <!--<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>-->
        <script type="text/javascript" src="js/popper.min.js"></script>  
        <script type="text/javascript">
            $(document).ready(function () {
                // Initialize tooltips & popovers
                var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
                var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                    return new bootstrap.Tooltip(tooltipTriggerEl);
                });
                var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
                var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
                    return new bootstrap.Popover(popoverTriggerEl);
                });
                // end of initializing

                getGreetingByID("greetings");
                setInterval(function () {
                    getGreetingByID("greetings");
                }, 60000);

                $("#alert-panel").click(function () {
                    $("#alert-panel").fadeOut(500);
                });
            });
            /*
             * For custom alerts notifications, use any of the functions below:
             * displayNotice(topic, msg);
             * displayNotice(topic, msg, topicid, msgid, panelid);
             */


        </script>
    </head>
    <body>
        <div class="overlay display-none" id="alert-panel">
            <div class=" centralize middle width-30 p-4 bg-light rounded">
                <div class="text-bold" id="alert-title">Message </div>
                <hr class="d-grid mb-2">
                <div class="p-2" id="alert-notice"></div>
            </div>
        </div>
        <!--============================ end of notification section ==================================-->
        <section id="banner" class="border-bottom bg-white p-2 text-color-1 text-22px mb-5">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-4 col-md-4 col-sm-12">
                        
                    </div>
                    <div class="col-lg-4 col-md-4 col-sm-12">
                        
                    </div>
                </div>
            </div>
        </section>
        <section id="welcome-banner" class="bg-color-irish-coffee bg-gradient text-white height-auto p-2 mb-3">
            <div class="width-80 centralize">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-2 col-md-12 col-sm-12">
                            <span class="position-relative">
                                <a href="#" class="text-decoration-none">
                                    <img src="<%= domain %>images/nafa_logo.png" alt="alt" class="w-150px height-150px rounded"/>
                                </a>
                            </span>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-12">
                            <div class="width-100 height-100 d-flex align-items-center p-3">
                                <h1 class="">Welcome to NAFA Coaches Portal Login Page</h1>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
            
        </section>
        <section id="content" class="mb-5">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-12">
                        <div class="alert alert-warning alert-dismissible fade <%= disp %>" role="alert" id="msg-alert">
                            <strong id="msgs"><%= msg %></strong>
                            <button type="button" class="btn btn-close pull-right close" data-bs-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true"></span>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="width-80 centralize shadow bg-white border-top-radius-10px border-bottom-radius-40px border">
                <div class="container-fluid">
                    
                    <div class="row pt-4 mb-5">
                        <div class="col-lg-8 col-md-8 col-sm-12">
                            <!--content section area-->
                            <div class="container">
                                <div class="row">
                                    <div class="col-12">
                                        <div class="height-600px width-100">
                                            <div class="width-70 height-100 centralize d-flex align-items-center justify-content-center">
                                                <img src="<%= domain %>images/nafa_full.png" alt="alt" class="width-100 height-90 rounded"/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-4 col-sm-12">
                            <!--login/registration form section area -->
                            <div class="container mt-5">
                                <div class="row mb-5">
                                    <div class="col-12">
                                        <div class="width-20 centralize">
                                            <span class="fa fa-user-group fa-5x"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-12">
                                        <form action="<%= AppData.COACH_CONTROLLER_SUBDOMAIN %>coach-Login" method="post">
                                            <div class="row mb-3">
                                                <div class="col-12">
                                                    <label for="email">Email:</label>
                                                    <div class="input-group">
                                                        <span class="input-group-text">
                                                            <span class="fa fa-user"></span>
                                                        </span>
                                                        <input type="email" class="form-control" name="textEmail" id="email" placeholder=" email here" required />
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-3">
                                                <div class="col-12">
                                                    <label for="email">Password:</label>
                                                    <div class="input-group">
                                                        <span class="input-group-text">
                                                            <span class="fa fa-user"></span>
                                                        </span>
                                                        <input type="password" class="form-control" name="textPassword" id="email" placeholder=" password here" required />
                                                    </div>
                                                </div>
                                            </div>
                                            
                                            <div class="row mb-3">
                                                <div class="col-12">
                                                    <div class="d-grid">
                                                        <button type="submit" class="btn btn-dark bg-color-irish-coffee btn-sm text-white border-bottom-color-irish_coffee">
                                                            <span>Sign in to dashboard</span>
                                                        </button>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row mb-3 mt-5">
                                                <div class="col-12">
                                                    <input type="hidden" name="function" value="login-1" />
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>
        <div class="position-relative bottom-0 height-auto width-100 bg-color-irish-coffee bg-gradient p-3 text-white">
            <div class="container-fluid">
                <div class="col-12 text-center">
                    Copyright &copy; 2023 NAFA. Developed and Powered By Jovine360 Solutions Limited
                </div>
            </div>
        </div>
        

        <script type="text/javascript" src="js/jquery-3.6.0.min.js"></script>
        <script type="text/javascript" src="js/popper.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.min.js"></script>
        <script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
    </body>
</html>
