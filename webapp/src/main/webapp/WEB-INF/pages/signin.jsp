<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Signin Template for Bootstrap</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/app/signin.css" rel="stylesheet">

    <script src="js/jquery-2.1.4.min.js" type='text/javascript'></script>
    <script src="js/app/signin.js" type='text/javascript'></script>
  </head>

  <body>

    <div class="container">

      <form id ="singin" class="form-signin" role="form">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="email" class="form-control" placeholder="Email address" required autofocus>
        <input type="password" class="form-control" placeholder="Password" required>
        <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>
        <button class="btn btn-lg btn-primary btn-block" id = "submitButton" type="submit">Sign in</button>
      </form>

    </div>

  </body>
</html>