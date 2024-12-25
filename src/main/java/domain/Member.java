// 8-2 会員データを格納する Member クラスを作成
// 8-3 会員⼀覧表示で、会員種別を ID ではなく種別名を表示するように変更
package domain;

import java.util.Date;

public class Member {

	// フィールド
	private Integer id;
	private String name;
	private Integer age;
	private String address;
	private Integer typeId;
	private Date created;
	private String typeName;

	// コンストラクタ
	public Member() {
		// 引数なしのデフォルトコンストラクタです。
		// 何も処理をしていませんが、クラスのインスタンスを生成するために用意されています。
		// このコンストラクタの役割:
		// 例えば、インスタンス生成時にすぐに値を設定しない場合や、初期処理が不要な場合に使われます。
	}
	
	// ↑デフォルトコンストラクタ: 引数なしでオブジェクトを生成するために使用。
	// ↓引数付きコンストラクタ: オブジェクト生成時にフィールドへ初期値を設定するために使用。

	public Member(Integer id, String name, Integer age, String address, Integer typeId, String typeName, Date created) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
		this.typeId = typeId;
		this.typeName = typeName;
		this.created = created;
		// 引数付きのコンストラクタ
	}

	// アクセッサ
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
