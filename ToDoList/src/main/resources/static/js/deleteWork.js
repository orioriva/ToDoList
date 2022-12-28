'use strict'

/** 画面ロード時の処理 */
window.addEventListener('DOMContentLoaded', function(){
	jQuery(function($){
		/** 送信ボタンを押した時の処理 */
		$('#btn-send').click(function(event) {
			// データ送信
			$('#input-form').submit();
		});
	});
});