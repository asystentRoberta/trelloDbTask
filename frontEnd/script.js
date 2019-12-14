$(document).ready(function () {

    var apiRoot = 'http://192.168.1.217:8080/trelloDbTask';
    var datatableRowTemplate = $('[data-datatable-row-template]').children()[0];
    var tasksContainer = $('[data-tasks-container]');

    var endPointGetTasks = '\\getAllTasks';
    var endPointUpdateTask = '\\updateExistingTask';
    var endPointDeleteTask = '\\deleteSingleTaskById';
    var endPointCreateTask = '\\createSingleTask';

    getAllTasks();

    function createElement(data)
    {
        var element = $(datatableRowTemplate).clone();

        element.attr('data-task-id', data.id);
        element.find('[data-task-name-section] [data-task-name-paragraph]').text(data.title);
        element.find('[data-task-name-section] [data-task-name-input]').val(data.title);

        element.find('[data-task-content-section] [data-task-content-paragraph]').text(data.content);
        element.find('[data-task-content-section] [data-task-content-input]').val(data.content);

        return element;
    }

    function handleDatatableRender(data)
    {
        tasksContainer.empty();
        data.forEach(function (task) {
            createElement(task).appendTo(tasksContainer);

        });
    }

    function getAllTasks()
    {
        var requestUrl = apiRoot + endPointGetTasks;

        $.ajax({
            url: requestUrl,
            method: 'GET',
            success: handleDatatableRender
        });
    }

    function handleTaskUpdateRequest()
    {
        var parentEl = $(this).parent().parent();
        var taskId = parentEl.attr('data-task-id');
        var taskTitle = parentEl.find('[data-task-name-input]').val();
        var taskContent = parentEl.find('[data-task-content-input]').val();
        var requestUrl = apiRoot + endPointUpdateTask;

        $.ajax({
            url: requestUrl,
            method: "PUT",
            processData: true,
            contentType: "application/json; charset=utf-8",
            dataType: 'text json',
            data: JSON.stringify({
                    id: taskId,
                    title: taskTitle,
                    content: taskContent
                }
            ),
            success: function (data) {

                parentEl.find('[data-task-name-paragraph]').text(taskTitle);
                parentEl.find('[data-task-content-paragraph]').text(taskContent);
                parentEl.attr('data-task-id', data.id).toggleClass('datatable__row--editing');

            },
        });
    }

    function handleTaskDeleteRequest()
    {
        var parentEl = $(this).parent().parent();
        var taskId = parentEl.attr('data-task-id');
        var requestUrl = apiRoot + endPointDeleteTask;

        $.ajax({
            url: requestUrl + '?taskId=' + taskId,
            method: 'DELETE',
            success: function () {
                parentEl.slideUp(400, function () {
                    parentEl.remove();

                })

            }
        })
    }

    function handleTaskSubmitRequest(event)
    {
        event.preventDefault();

        var taskTitle = $(this).find('[name="title"]').val();
        var taskContent = $(this).find('[name = "content"]').val();
        var requestUrl = apiRoot + endPointCreateTask;

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify(({
                title: taskTitle,
                content: taskContent
            })),
            complete: function (data) {
                if (data.status === 200) {
                    getAllTasks();
                }
            }
        })
    }

    function toggleEditingState()
    {
        var parentEl = $(this).parent().parent();
        parentEl.toggleClass('datatable__row--editing');

        var taskTitle = parentEl.find('[data-task-name-paragraph]').text();
        var taskContent = parentEl.find('[data-task-content-paragraph]').text();

        parentEl.find('[data-task-name-input]').val(taskTitle);
        parentEl.find('[data-task-content-input]').val(taskContent);
    }

    $('[data-task-add-form]').on('submit', handleTaskSubmitRequest);

    tasksContainer.on('click', '[data-task-edit-button]', toggleEditingState);
    tasksContainer.on('click', '[data-task-edit-abort-button]', toggleEditingState);
    tasksContainer.on('click', '[data-task-submit-update-button]', handleTaskUpdateRequest);
    tasksContainer.on('click', '[data-task-delete-button]', handleTaskDeleteRequest);

})
;

