<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-start" style="object-fit: cover; opacity: 0.7;" alt="">
                    </div>
                    <div class="col-10">
                        <div class="border-bottom border-top border-primary bg-light py-5 px-4">
                            <div class="text-center">
                                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Welcome</small>
                                <h1 class="display-5 mb-5">Sign in using your credentials</h1>
                            </div>
                            <div class="row g-4 form">
                                <div class="col-lg-3 col-md-6">
    
                                </div>
                                <div class="col-lg-6 col-md-6">                            
                                        <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Username">                  
                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col-lg-6 col-md-6">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Password">
                                </div>
                                <div class="col-lg-3 col-md-6">

                                </div>
                                <div class="col">
                                </div>
                                <div class="col">
                                </div>
                                <div class="col text-center stackpanel">
                                    <button type="submit" class="btn btn-primary px-5 py-3 rounded-pill">Sign In</button>
                                    <br/>
                                    <label>Don't have an account? <router-link :to="'/register'"><b>Register now.</b></router-link></label>
                                </div>
                                <div class="col">
                                </div>
                                <div class="col">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-end" style="object-fit: cover; opacity: 0.7;" alt="">
                    </div>
                </div>
            </div>
        </div>
</template>

<style scoped>
    .stackpanel{
        display: flex;
        flex-direction: column;
    }
</style>