$().ready(function(){
    
    $("#userDetailsForm").validate({
       rules:{
           password:{
               required: true,
               minlength: 6
           },
           email:{
               required: true,
               email: true
           }
       } 
    });
});

