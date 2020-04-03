

<div  id="users" style="display: none">

    <table style="width: auto">
        <thead>
        <tr>
            <td style="width: 50px">Id</td>
            <td style="width: 150px">Name</td>
            <td style="width: 100px">Age</td>
            <td style="width: 150px">login</td>
            <td style="width: 100px">password</td>

        </tr>
        </thead>
        <tbody>
        <tr>
            <#list usersList>

                <#items as user>
        <tr>
            <td> ${user.id} </td>
            <td> ${(user.name)!"-"} </td>
            <td> $${(user.age)!} </td>
            <td> ${user.login} </td>
            <td> ${user.password} </td>
        </tr>
            </#items>

            </#list>
        </tr>
        </tbody>
    </table>
</div>


