'use strict'

/** 画面ロード時の処理 */
window.addEventListener('DOMContentLoaded', function(){
	jQuery(function($){
		/** 登録ボタンを押した時の処理 */
		$('#btn-register').click(function(event) {
			registerData();
		});

		/** 更新ボタンを押した時の処理 */
		$('#btn-update').click(function(event) {
			updateData();
		});

		/** 削除ボタンを押した時の処理 */
		$('#btn-delete').click(function(event) {
			deleteData();
		});
	});
});

/** 処理後遷移ページの設定 */
function getReturnPage(){
	return $('#returnPage').val();
}

/** 作業登録処理 */
function registerData(){
	// バリデーション結果をクリア
	removeValidResult();

	// フォームの値を取得
	var formData = $('#input-form').serializeArray();

	// ajax通信
	$.ajax({
		type : "POST",
		cache : false,
		url : '/inputWork/register',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('作業を登録しました');
			// 作業一覧画面にリダイレクト
			window.location.href = getReturnPage();
		}else{
			errorProc(data);
		}
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 作業更新処理 */
function updateData(){
	// バリデーション結果をクリア
	removeValidResult();

	// フォームの値を取得
	var formData = $('#input-form').serializeArray();

	// ajax通信
	$.ajax({
		type : "PUT",
		cache : false,
		url : '/inputWork/update',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('作業を更新しました');
			// 作業一覧画面にリダイレクト
			window.location.href = getReturnPage();
		}else{
			errorProc(data);
		}
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 作業削除処理 */
function deleteData(){
	// ajax通信
	$.ajax({
		type : "DELETE",
		cache : false,
		url : '/inputWork/delete',
		data : null,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('作業を削除しました');
			// 作業一覧画面にリダイレクト
			window.location.href = getReturnPage();
		}else{
			errorProc(data);
		}
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 共通エラー処理 */
function errorProc(data){
	if(data.result == 90){
		// validationエラー時の処理
		$.each(data.errors, function(key, value){
			reflectValidResult(key, value)
		});
	}else if(data.result == 404){
		alert('作業が既に削除されています');
		window.location.href = getReturnPage();
	}else{
		alert('不明なエラーが発生しました');
		window.location.href = getReturnPage();
	}
}

/** バリデーション結果をクリア */
function removeValidResult(){
	$('.is-invalid').removeClass('is-invalid');
	$('.invalid-feedback').remove();
	$('.text-danger').remove();
}

/** バリデーション結果の反映 */
function reflectValidResult(key, value){
	if(key === 'managerName'){	// 担当者選択の場合
		// CSS適用
		$('select[id=' + key + ']').addClass('is-invalid');
		// エラーメッセージ追加
		$('div[id=errorMsg]').append('<div class="text-danger">' + value + '</div>');
	}else{
		// CSS適用
		$('input[id=' + key +']').addClass('is-invalid');
		// エラーメッセージ追加
		$('div[id=errorMsg]').append('<div class="text-danger">' + value + '</div>');
	}
}
