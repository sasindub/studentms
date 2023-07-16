$(document).ready(function(){
    
    $("#load").hide();
    //get All Data
    getAllData();
    function getAllData(){
        $.ajax({
            url:"http://localhost:8080/api/v1/studentms/getAllStudent",
            method:"get",
            contentType:"application/json",
            async:true,
            beforeSend:function(){
                $("#load").show();
                $("#content").hide();
            },
            success: function(data){

                if(data.code == "00"){
                    var tbody ;
                    var lastStudent;
                    for(let stu of data.content){
                        tbody += `<tr><td scope="row">${stu.stuId}</td>
                        <td>${stu.stuName}</td>
                        <td>${stu.stuMobile}</td>
                        <td>${stu.stuPaidMonth}</td>
                        <td><a class="btnDel" id="${stu.stuId}" style="color:red;" title="Delete the student"><i class="fa-solid fa-square-minus"></i></a></td>
                        </tr>`

                        lastStudent = stu.stuId;
                    }
                    $("#id").val(lastStudent + 1);
                    $("#tbody").html(tbody);
                }else{
                    $("#tbody").html("<tr><td>Error!!!!</td></tr>");
                    alert(data.message);
                }

                $("#load").hide();
                $("#content").show();
               
            },
            error:function(error){
                console.log(error);
            }
            
        });
    }

    //Insert
    $("#save").on("click",function(e){
        e.preventDefault();
        let name = $("#name").val();
        let mobile = $("#mobile").val();

        $.ajax({
            url:"http://localhost:8080/api/v1/studentms/saveStudent",
            method:"post",
            contentType:"application/json",
            async:true,
            data:JSON.stringify({
                "stuId":"",
                "stuName":name,
                "stuMobile":mobile,
                "stuPaidMonth":"00"
            }),
            beforeSend:function(){
                $("#load").show();
                $("#content").hide();
            },
            success:function(data){
                if(data.code == "00"){
                    alert("Saved!");
                    $("#load").hide();
                    $("#content").show();
                    getAllData();
                }else{
                    alert("Something went wrong!");
                }
                $("#stFrm").trigger("reset");
            },
            error:function(error){
                console.log(error);
            }
        });
    });

    //Delete
    $(document).on("click",".btnDel", function(e){
        $('#delModel').modal('show');
        $('#stuId').val(this.id);
    });


    $("#modelYes").on("click", function(e){
        e.preventDefault();
        let studentId = $('#stuId').val();
        $.ajax({
            url:"http://localhost:8080/api/v1/studentms/studentDelete/"+studentId,
            method:"delete",
            contentType:"application/json",
            success:function(data){
                $('#delModel').modal('hide');
                if(data.code == "00"){
                    alert(`Student (${studentId}) Deleted Successfully`);
                }else{
                    alert("Something went wrong!");
                }
                getAllData();
            },
            error:function(error){
                console.log(error);
            }
        });

    });

    $("#modelNo").on("click", function(e){
        e.preventDefault();
        $('#delModel').modal('hide');
    });

    //Search
    $("#search").on("keyup", function(e){
        let searchedVal = $("#search").val();

        if(searchedVal !== ""){
            $.ajax({
                url:"http://localhost:8080/api/v1/studentms/search/"+searchedVal,
                method:"get",
                contentType:"application/json",
                success:function(data){
                    if(data.code == "00"){
                        var tbody;
                        for(stu of data.content){
                            tbody += `<tr><td scope="row">${stu.stuId}</td>
                            <td>${stu.stuName}</td>
                            <td>${stu.stuMobile}</td>
                            <td>${stu.stuPaidMonth}</td>
                            <td><a class="btnDel" id="${stu.stuId}" style="color:red;" title="Delete the student"><i class="fa-solid fa-square-minus"></i></a></td>
                            </tr>`
                        }

                        $("#tbody").html(tbody);
                    }else{
                        $("#tbody").html("<tr><td></td><td></td><td style='text-align:center;'>No Data Found!!!</td><td></td><td></td></tr>");
                    }
                },
                error:function(error){
                    console.log(error);
                }
            });
        }else{
            getAllData();
        }
    });

    //click on table
    $(document).on("click", "#tbl tr", function(){
        var id = $(this).find('td:eq(0)').text();
        var name = $(this).find('td:eq(1)').text();
        var mobile = $(this).find('td:eq(2)').text();
        var paidMonth = $(this).find('td:eq(3)').text();

        $("#id").val(id);
        $("#name").val(name);
        $("#mobile").val(mobile);
        $("#paidMonth").val(paidMonth);
    });

    //update
    $("#update").on("click", function(e){
        e.preventDefault();
        let stuId = $("#id").val();
        let stuName = $("#name").val();
        let stuMobile = $("#mobile").val();
        let paidMonth = $("#paidMonth").val();
        $.ajax({
            url: "http://localhost:8080/api/v1/studentms/updateStudent",
            method: "put",
            contentType: "application/json",
            data: JSON.stringify({
                "stuId":stuId,
                "stuName":stuName,
                "stuMobile":stuMobile,
                "stuPaidMonth":paidMonth
            }),
            success: function(data){
                if(data.code == "00"){
                    $("#stFrm").trigger("reset");
                    alert(`Updated! (Student ID: ${stuId} )`);
                    getAllData();
                }else{
                    alert("No Data Found!!");
                }
            },
            error: function(data){
                console.log(data);
            }
        });
        
        
    });

});