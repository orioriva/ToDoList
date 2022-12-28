'use strict'

/** 画面ロード時の処理 */
window.addEventListener('DOMContentLoaded', function(){
	jQuery(function($){
		/** 送信ボタンを押した時の処理 */
		$('#btn-send').click(function(event) {
			// データ送信
			sendData();
		});
	});
});

/** データ送信処理 */
function sendData(){
	// バリデーション結果をクリア
	removeValidResult();

	// フォームの値を取得
	var formData = $('#input-form').serializeArray();

	// ajax通信
	$.ajax({
		type : "POST",
		cache : false,
		url : '/login/rest',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 90){
			// validationエラー時の処理
			$.each(data.errors, function(key, value){
				reflectValidResult(key, value)
			});
		}else if(data.result == 0){
			// 入力情報を送信
			$('#input-form').submit();
		}
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** バリデーション結果をクリア */
function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}

/** バリデーション結果の反映 */
function reflectValidResult(key, value){
	// CSS適用
	$('input[id=' + key +']').addClass('is-invalid');
	// エラーメッセージ追加
	$('div[id=errorMsg]').append('<div class="text-danger">' + value + '</div>');
}
