<template>
    <div class="container-fluid contact py-6 wow bounceInUp" data-wow-delay="0.1s">
            <div class="container">
                <div class="row g-0">
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-start" style="object-fit: cover; opacity: 0.8;" alt="">
                    </div>
                    <div class="col-10">
                        <div class="border-bottom border-top border-primary bg-light py-5 px-4">
                            <div class="text-center">
                                <small class="d-inline-block fw-bold text-dark text-uppercase bg-light border border-primary rounded-pill px-4 py-1 mb-3">Welcome</small>
                                <h1 class="display-5 mb-5">Sign up</h1>
                            </div>
                            <div class="row g-4 form">
                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Username">
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Name">
                                </div>
                                <div class="col-1">
                                </div>


                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <input type="password" class="form-control border-primary p-2" placeholder="Enter Your Password">
                                </div>
                                <div class="col-2">
                                </div>
                                <div class="col-4">
                                    <input type="text" class="form-control border-primary p-2" placeholder="Enter Your Surname">
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-1">
                                </div>
                                <div class="col-4">
                                    <label></label>
                                    <input type="password" class="form-control border-primary p-2" placeholder="Repeat Your Password">
                                </div>
                                <div class="col-2">
                                </div>
                                <div class="col-2">
                                    <label>Select gender</label>
                                    <select class="form-select border-primary p-2" aria-label="Gender">
                                        <option value="1" selected>Male</option>
                                        <option value="2">Female</option>
                                    </select>
                                </div>
                                <div class="col-2">
                                    <label>Enter birth date</label>
                                    <input type="date" class="form-control border-primary p-2" placeholder="Birth Date">
                                </div>
                                <div class="col-1">
                                </div>

                                <div class="col-12 text-center">
                                    <br/>
                                    <button type="submit" class="btn btn-primary px-5 py-3 rounded-pill">Register Now</button>
                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="col-1">
                        <img src="../img/background-site.jpg" class="img-fluid h-100 w-100 rounded-end" style="object-fit: cover; opacity: 0.8;" alt="">
                    </div>
                </div>
            </div>
        </div>
</template>

<script setup>

</script>

<style scoped>
    .stackpanel{
        display: flex;
        flex-direction: column;
    }
</style>
