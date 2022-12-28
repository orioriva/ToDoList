'use strict'

/** 画面ロード時の処理 */
window.addEventListener('DOMContentLoaded', function(){
	jQuery(function($){
		/** 完了ボタンを押した時の処理 */
		$('button.btn-info').click(function(event) {
			completeOne();
		});

		/** 表示件数を変更した時の処理 */
		$('#limitSelect').on('change',function(event) {
			changeViewLimit();
		});

		/** 表示ページを変更した時の処理 */
		$('#form-pageSelect button').on('click',function(event) {
			changeViewPage();
		});
	});
});

/** 処理後遷移ページの設定 */
function getReturnPage(){
	return $('#returnPage').val();
}

/** 完了ボタンを押した時の処理 */
function completeOne(){
	//要素を取得
	var obj = event.target;

	// フォームの値を取得
	var formData = $(obj).parent().serializeArray();

	// ajax通信
	$.ajax({
		type : "PUT",
		cache : false,
		url : '/inputWork/complete',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 404){
			alert('削除された作業です');
		}else if(data.result == 410){
			alert('既に完了している作業です');
		}else if(data.result == 0){

		}else{
			alert('不明なエラーが発生しました');
		}
		window.location.reload();
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 表示件数を変更したとき */
function changeViewLimit(){
	// ajax通信
	$.ajax({
		type : "POST",
		cache : false,
		url : '/workList/changeViewLimit',
		data : $('#limitSelect'),
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		window.location.reload();
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 表示ページを変更したとき */
function changeViewPage(){
	// ajax通信
	$.ajax({
		type : "POST",
		cache : false,
		url : '/workList/changeViewPage',
		data : $(event.target),
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		window.location.href = getReturnPage();
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}