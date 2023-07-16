$(document).ready(function(){
  
    getAllEmp();
function getAllEmp(){
    $.ajax({
        url:"http://localhost:8080/api/v1/emp/getAllEmployee",
        method:"get",
        contentType:"application/json",
        async:true,
        success:function(data){
           
            $("#frm").trigger('reset');
           let body;
            if(data.code == "00"){

                for(let emp of data.content){
                    body += ` <tr>
                    <td scope="row">${emp.empId}</td>
                    <td>${emp.empName}</td>
                    <td>${emp.empAddress}</td>
                    <td>${emp.empMobile}</td>
                </tr>`;
                }
          
            $("#tbody").html(body);

            }

           
            
           

            
        },
        error:function(error){
            console.log(error);
            alert("Error");
        }


    });

}


    //submit button
    $("#btnSubmit").on("click", ()=>{
        let empName    = $("#name").val();
        let empAddress = $("#address").val();
        let empMobile  = $("#mobile").val();
        let empId      = $("#id").val();
    
        $.ajax({
            url:"http://localhost:8080/api/v1/emp/saveEmployee",
            method:"post",
            contentType:"application/json",
            async:true,
            data:JSON.stringify({
                "empId":"",
                "empName":empName,
                "empAddress":empAddress,
                "empMobile":empMobile
            }),
            success:function(data){
                alert("Saved!");
                $("#frm").trigger('reset');
                getAllEmp();
            },
            error:function(error){
                console.log(error);
            }


        });
    });

    //delete button
    $("#btnDel").on("click", ()=>{
       
    
        let empId      = $("#id").val();
    
        $.ajax({
            url:"http://localhost:8080/api/v1/emp/empDelete/"+empId,
            method:"delete",
            contentType:"application/json",
            async:true,
            success:function(data){
                
                alert(data.message);
                $("#frm").trigger('reset');
                getAllEmp();
            },
            error:function(error){
                console.log(error);
                alert("Error");
            }


        });
    });

    //update button
    $("#btnUpdate").on("click", ()=>{
        let empName    = $("#name").val();
        let empAddress = $("#address").val();
        let empMobile  = $("#mobile").val();
        let empId      = $("#id").val();
    
        $.ajax({
            url:"http://localhost:8080/api/v1/emp/updateEmployee",
            method:"put",
            contentType:"application/json",
            async:true,
            data:JSON.stringify({
                "empId":empId,
                "empName":empName,
                "empAddress":empAddress,
                "empMobile":empMobile
            }),
            success:function(data){
                
                alert(data.message);
                $("#frm").trigger('reset');
                getAllEmp();
            },
            error:function(error){
                console.log(error);
                alert("Error");
            }


        });

    });

    //search button
    $("#btnSearch").on("click", ()=>{
        let empId      = $("#id").val();
    
        $.ajax({
            url:"http://localhost:8080/api/v1/emp/getEmpById/"+empId,
            method:"get",
            contentType:"application/json",
            async:true,
            success:function(data){
                
                $("#frm").trigger('reset');
                if(data.content != null){
                let body = ` <tr>
                <td scope="row">`+data.content.empId+`</td>
                <td>`+data.content.empName+`</td>
                <td>`+data.content.empAddress+`</td>
                <td>`+data.content.empMobile+`</td>
              </tr>`;
              $("#tbody").html(body);
                }else{
                    alert(data.message);
                }

                
            },
            error:function(error){
                console.log(error);
                alert("Error");
            }


        });
    });

    $(document).on("click", "#tbl tr", function(){
        var col0 = $(this).find('td:eq(0)').text();
        var col1 = $(this).find('td:eq(1)').text();
        var col2 = $(this).find('td:eq(2)').text();
        var col3 = $(this).find('td:eq(3)').text();

        $("#id").val(col0);
        $("#name").val(col1);
        $("#address").val(col2);
        $("#mobile").val(col3);

    });
    
});