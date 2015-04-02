<%@ page import="org.wso2.carbon.andes.stub.AndesAdminServiceStub" %>
<%@ page import="org.wso2.carbon.andes.stub.admin.types.QueueRolePermission" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.wso2.carbon.andes.ui.UIUtils" %>
<%@ page import="org.wso2.carbon.andes.stub.AndesAdminServiceBrokerManagerAdminException" %>
<%
    AndesAdminServiceStub stub = UIUtils.getAndesAdminServiceStub(config, session, request);
    String message = "";
    boolean exclusiveConsumer;

    String queue = (String) session.getAttribute("queue");
    String permissions = request.getParameter("permissions");
    String[] permissionParams = new String[0];
    if (permissions != null && !"".equals(permissions)) {
         permissionParams = permissions.split(",");
    }

    if(null!=request.getParameter("isExclusiveConsumer")) {
            exclusiveConsumer = true;
   }
   else{
           exclusiveConsumer = false;
   }

    ArrayList<QueueRolePermission> queueRolePermissionArrayList = new ArrayList<QueueRolePermission>();
    for (int i = 0; i < permissionParams.length; i++) {
        String role = permissionParams[i];
        i++;
        String allowedCon = permissionParams[i];
        i++;
        String allowedPub = permissionParams[i];
        QueueRolePermission queueRolePermission = new QueueRolePermission();
        queueRolePermission.setRoleName(role);
        queueRolePermission.setAllowedToConsume(Boolean.parseBoolean(allowedCon));
        queueRolePermission.setAllowedToPublish(Boolean.parseBoolean(allowedPub));
        queueRolePermissionArrayList.add(queueRolePermission);
    }
    session.removeAttribute("queueRolePermission");

    QueueRolePermission[] queueRolePermissions = new QueueRolePermission[queueRolePermissionArrayList.size()];
    try {
        stub.updatePermissionWithExclusiveConsumer(queue, queueRolePermissionArrayList.toArray(queueRolePermissions),
         exclusiveConsumer);
        message = "Queue added successfully";
    } catch (AndesAdminServiceBrokerManagerAdminException e) {
        message = "Error in adding/updating permissions : " + e.getMessage();
    }
    session.setAttribute("queueRolePermission", stub.getQueueRolePermission(queue));
%><%=message%>
