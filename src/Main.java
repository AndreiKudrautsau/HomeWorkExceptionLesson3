/*Напишите приложение, которое будет запрашивать у пользователя следующие данные, разделенные пробелом:
	Фамилия Имя Отчество датарождения номертелефона пол
	Форматы данных:
	фамилия, имя, отчество - строки
	датарождения - строка формата dd.mm.yyyy
	номертелефона - целое беззнаковое число без форматирования
	пол - символ латиницей f или m.
	ЧТОБЫ НЕ УСЛОЖНЯТЬ ВСЕ МОЖЕТ БЫТЬ String;
	Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, бросить исключение, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
	Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
	Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
	Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
	Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
	<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
	Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
	Не забудьте закрыть соединение с файлом.
	При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.
*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Main {
	public static void main(String[] args) throws IOException {

		try {
			newNote();
			System.out.println("Запись успешно добавлена в файл");
		}
		catch (Exception e){
			System.out.println(e.getStackTrace());
		}

	}

	public static void newNote() throws Exception{
		System.out.println("Введите через пробел: фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (число без разделителей) и пол (символ латиницей f или m)");

		String text;
		Scanner scan = new Scanner(System.in);
		text = scan.nextLine ();

		String[] array = text.split(" ");
		if (array.length != 6){
			throw new Exception("Введено неверное количество параметров");
		}

		String surname = array[0];
		String name = array[1];
		String patronymic = array[2];
		String birthDay = array[3];
		String numberPhone = array[4];
		String gender = array[5];

		try{
			SimpleDateFormat format = new SimpleDateFormat(birthDay);
		} catch (DateFormatException e){
			throw new DateFormatException();
		}

		try{
			Integer.parseInt(numberPhone);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Введенный номер телефона содержит не только цифры");
		}

		if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")){
			throw new RuntimeException("Неверно введен пол");
		}

		String fileName = "src\\" + surname.toLowerCase() + ".txt";
		File file = new File(fileName);
		try (FileWriter fileWriter = new FileWriter(file, true)){

			if (file.length() > 0){
				fileWriter.write('\n');
			}
			fileWriter.write(String.format("<%s> <%s> <%s> <%s> <%s> <%s>", surname, name, patronymic, birthDay, numberPhone, gender));
		}catch (IOException e){
			throw new FileSystemException("Возникла ошибка при работе с файлом");
		}
	}
}