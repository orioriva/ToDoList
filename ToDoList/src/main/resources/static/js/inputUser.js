'use strict'

/** 画面ロード時の処理 */
window.addEventListener('DOMContentLoaded', function(){
	jQuery(function($){
		/** パスワード変更をチェックした時の処理 */
		$('#password-check').change(function(event) {
			if($('#password-check').prop('checked') == true){
				$('#password-form').show();
			}else{
				$('#password-form').hide();
			}
		});

		/** 更新ボタンが押された時の処理 */
		$('#btn-update').click(function(event){
			updateUser();
		});

		/** 登録ボタンが押された時の処理 */
		$('#btn-register').click(function(event){
			addUser();
		});

		/** 削除ボタンが押された時の処理 */
		$('#btn-delete').click(function(event){
			deleteUser();
		});

		/** パスワードリセットボタンが押された時の処理 */
		$('#btn-reset').click(function(event){
			if(window.confirm('本当にパスワードを初期化してよろしいですか？\r\n※ ランダム文字列の暫定パスワードを発行・登録します')){
				resetPassword();
			}
		});
	});
});

/** 処理後遷移ページの設定 */
function getReturnPage(){
	return $('#returnPage').val();
}

/** 登録ボタンが押された時の処理 */
function addUser(){
	// バリデーション結果をクリア
	removeValidResult();

	// フォームの値を取得
	var formData = $('#input-form').serializeArray();

	// ajax通信
	$.ajax({
		type : "POST",
		cache : false,
		url : '/admin/rest/addUser',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('ユーザーを新規登録しました');
			// ユーザー一覧画面にリダイレクト
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

/** 更新ボタンが押された時の処理 */
function updateUser(){
	// バリデーション結果をクリア
	removeValidResult();

	// フォームの値を取得
	var formData = $('#input-form').serializeArray();

	// ajax通信
	$.ajax({
		type : "PUT",
		cache : false,
		url : '/admin/rest/updateUser',
		data : formData,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('登録内容を更新しました');
			// ユーザー一覧画面にリダイレクト
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

/** パスワードリセットボタンが押された時の処理 */
function resetPassword(){
	// ajax通信
	$.ajax({
		type : "PUT",
		cache : false,
		url : '/admin/rest/resetPassword',
		data : null,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 10){
			window.prompt('パスワードを更新しました\r\n暫定パスワード:', data.errors['msg']);
			window.location.href = getReturnPage();
		}
	}).fail(function(jqXHR, testStatus, errorThrown){
		// ajax失敗時の処理
		alert('情報送信に失敗しました');
	}).always(function(){
		// 常に実行する処理
	});
}

/** 削除ボタンが押された時の処理 */
function deleteUser(){
	// ajax通信
	$.ajax({
		type : "DELETE",
		cache : false,
		url : '/admin/rest/deleteUser',
		data : null,
		dataType : 'json',
	}).done(function(data){
		// ajax成功時の処理
		console.log(data);

		if(data.result == 0){
			alert('ユーザーを削除しました');
			// ユーザー一覧画面にリダイレクト
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
		alert('既に削除されています');
		window.location.href = getReturnPage();
	}else if(date.result == 999){
		alert('自身を削除する事は出来ません');
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
	// CSS適用
	$('input[id=' + key +']').addClass('is-invalid');
	// エラーメッセージ追加
	$('div[id=errorMsg]').append('<div class="text-danger">' + value + '</div>');
}