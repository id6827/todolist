(function (window) {
	//'use strict';
	const all = 0;
	const active = 1;
	const completed = 2;
	flag = all;	// 전역변수

	//현재시간구하기 yyyy-mm-dd hh:mm:ss
	function getTimeStamp() {
	  var d = new Date();
	  var s =
	    leadingZeros(d.getFullYear(), 4) + '-' +
	    leadingZeros(d.getMonth() + 1, 2) + '-' +
	    leadingZeros(d.getDate(), 2) + ' ' +

	    leadingZeros(d.getHours(), 2) + ':' +
	    leadingZeros(d.getMinutes(), 2) + ':' +
	    leadingZeros(d.getSeconds(), 2);

	  return s;
	}
	function leadingZeros(n, digits) {
	  var zero = '';
	  n = n.toString();

	  if (n.length < digits) {
	    for (i = 0; i < digits - n.length; i++)
	      zero += '0';
	  }
	  return zero + n;
	}

	// CREATE
	function create() {
		var input = $('.new-todo');
		input.keydown(function(e) {
			if (e.which == 13) {
				if (input.val() != ""){
					//alert("CREATE");
					var json = {'todo':input.val(), 'completed':0,'date': new Date(getTimeStamp())};
					$.ajax({
						type : "POST",
						async : true, // false일경우 동기요청
						url : "api/todos/create",
						data : JSON.stringify(json),
						contentType: "application/json",
						processData: false,
						success : function(response) {
								console.log(response);
								input.val('');		// 입력창 초기화
								//alert(response);
								//findAll();
						}/*,success : function(response){ //통신 성공시 처리 },
						error : function(request, status, error) { //통신 에러 발생시 처리 },
						beforeSend : function(jqXHR, settings) { //통신을 시작할때 처리 },
						complete : function(jqXHR, textStatus) { //통신이 완료된 후 처리 }*/
					});
				}
			}
		});
	}
	// findAll
	function findAll(param) {
		$('.todo-list').empty();
		var todolist = $('.todo-list');
		var str;
		$.ajax({
			type : "GET",
			async : false, // false일경우 동기요청
			url : "api/todos",
			contentType: "application/json",
			processData: true,
			success : function(response) {
				$.each(response, function(index, obj){
					if (param == active){
						if (obj.completed == 0){
							todolist.append('<li> <div class="view"> <input class="toggle" type="checkbox"><label>'+obj.todo +'</label>'+'<input type="hidden" id="id" value="'+obj.id+'"/><button class="destroy"></button></div><input class="edit"></li>');
						}
					}
					else if (param == completed){
						if (obj.completed != 0){
							todolist.append('<li class="completed"> <div class="view"> <input class="toggle" type="checkbox" checked><label>'+obj.todo+'</label>'+'<input type="hidden" id="id" value="'+obj.id+'"/><button class="destroy"></button></div><input class="edit"></li>');
						}
					}
					else{
						if (obj.completed == 0){
							todolist.append('<li> <div class="view"> <input class="toggle" type="checkbox"><label>'+obj.todo +'</label>'+'<input type="hidden" id="id" value="'+obj.id+'"/><button class="destroy"></button></div><input class="edit"></li>');
						}
						else{
							todolist.append('<li class="completed"> <div class="view"> <input class="toggle" type="checkbox" checked><label>'+obj.todo+'</label>'+'<input type="hidden" id="id" value="'+obj.id+'"/><button class="destroy"></button></div><input class="edit"></li>');
						}
					}
				});
			},
			/*complete: function(){
				//document.location.reload();
				//location.reload();
				$('.todo-list').html(str);
			}*/
			});
		}
	// delete
	function destroy() {
		//$('.destroy').bind("click",function(){
		$(document).delegate(".destroy","click",function(){
			var num = $(this).closest('li').prevAll().length;		// 해당 리스트의 인덱스번호
			var id = $('.todo-list li div input#id')[num].value;	// 해당 인덱스에 id값
			$.ajax({
				type : "DELETE",
				async : true, // false일경우 동기요청
				url : "api/todos/" + id,
				success : function(response) {
					//alert(response);
					console.log(response);
				}});
     });
	}
	// update
	function put() {
		$(document).delegate(".toggle","click",function(){
			var tmp = flag;	// 현재 flag 임시 저장
			var num = $(this).closest('li').prevAll().length;		// 해당 리스트의 인덱스번호
			var checked = $('.todo-list li div input.toggle')[num].checked? 1:0;
			var id = $('.todo-list li div input#id')[num].value;
			var label = $('.todo-list li div ').text().split(' ');
			//alert(label[num+1]);		// label에선 인덱스 0에 ""가 존재함 ?? why??
			var json = {'id':id,'todo':label[num+1], 'completed':checked,'date': new Date(getTimeStamp())};
			$.ajax({
			type : "PUT",
			async : true, // false일경우 동기요청
			url : "api/todos/" + id,
			data : JSON.stringify(json),
			contentType: "application/json",
			processData: false,
			success : function() {
					flag = tmp;	// put 이후에 flag에 문제가 생기므로 tmp값으로 덮어씌움
			}/*,success : function(response){ //통신 성공시 처리 },
			error : function(request, status, error) { //통신 에러 발생시 처리 },
			beforeSend : function(jqXHR, settings) { //통신을 시작할때 처리 },
			complete : function(jqXHR, textStatus) { //통신이 완료된 후 처리 }*/
			});
		});
	}
	// 아이템 개수
	function itemCount() {
		var splits = $('.todo-count').text().split(' ');
		//splits[0] = $('.todo-list li').size();	// list 전체개수
		//splits[0] = $("input[class=toggle]:checked").size(); // 체크된 전체 개수
		splits[0] = $("input[class=toggle]:not(:checked)").size(); // 체크되지 않은 전체 개수
		splits = splits.toString().replace(/,/g, ' '); //, 패턴에 대해서 g (전역)검색 후 ' '로 치환
		$('.todo-count').text(splits);
	}
	// selected
	function setSelected() {
		$(document).delegate(".selected","click",function(){
			flag = all;
		});
	}
	// Active
	function setActive() {
		$(document).delegate(".active","click",function(){
			flag = active;
		});
	}
	// completed
	function setCompleted() {
		$(document).delegate(".completed","click",function(){
			flag = completed;
		});
	}
	// clear completed
	function clearCompleted() {
		$(document).delegate(".clear-completed","click",function(){
			$("ul[class=todo-list] li[class=completed] input[id=id]").each(function(i){
				var id = $(this).val();
				$.ajax({
					type : "DELETE",
					async : true, // false일경우 동기요청
					url : "api/todos/" + id,
					success : function(response) {
						//alert(response);
						console.log(response);
					}});
			});
		});
	}

	$(document).ready(function(){
		setCompleted();
		setActive();
		setSelected();
		clearCompleted();
		create();
		put();
		findAll(flag);
		destroy();
		itemCount();
	});
	// 부분 갱신
	setInterval(function(){
		findAll(flag);
		itemCount();
	}, 500);

//$("ul[class=todo-list] li[class=completed]")
/*// 체크된 아이템들만
$("ul[class=todo-list] li[class=completed]").each(function(i){
	console.log($(this).html());
});
*/
/*//체크되지않은 아이템들만
$("ul[class=todo-list] li").not("li[class=completed]").each(function(i){
	console.log($(this).html());
});
*/
})(window);
