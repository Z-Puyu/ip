# Sheogorath — User Guide

> A clone of the Daedric Prince’s sense of whimsy — this CLI Java chatbot brings the personality of Sheogorath the Mad God from the Elder Scrolls universe to your task management App. 
> Use it to add todos, manage deadlines and events while Sheogorath provides flavourful commentary.

## Quick start

1. Ensure you have **Java 17+** installed.
2. Place `sheogorath.jar` in a folder you want to use as the app home.
3. From that folder run the following from your command line:

```bash
java -jar sheogorath.jar
```

4. Type commands at the input text box and press **Enter**. Example:

```
todo Buy cheese
```

---

## Command Summary

| Action                      |                                                              Format | Example                                                     |
| --------------------------- |--------------------------------------------------------------------:| ----------------------------------------------------------- |
| Add todo                    |                                                       `todo <task>` | `todo Buy cheese`                                           |
| Add deadline                |                               `deadline <task> /by <date and time>` | `deadline Submit report /by 2024-03-15`                     |
| Add event                   | `event <event> /from <start date and time> /to <end date and time>` | `event Meeting /from 2024-03-20 14:00 /to 2024-03-20 16:00` |
| List tasks                  |                                                              `list` | `list`                                                      |
| Find tasks (case-sensitive) |                                                    `find <keyword>` | `find Report`                                               |
| Delete task                 |                                                    `delete <index>` | `delete 3`                                                  |
| Mark done                   |                                                      `mark <index>` | `mark 2`                                                    |
| Unmark done                 |                                                    `unmark <index>` | `unmark 2`                                                  |
| Exit                        |                                                               `bye` | `bye`                                                       |

---

## Features 

### Add a todo task: `todo`

Adds a new basic todo task.

**Format**

```
todo <task>
```

**Example**

```
todo Buy cheese
```

**Behaviour**

* Adds an unchecked todo with the provided name.
* If a task with the same type *and* identical name already exists, Sheogorath will detect the duplicate and refuse to add it (you'll see a warning). Use `list` or `find` to inspect existing tasks.

---

### Add a task with a deadline: `deadline`

Add a task that has a deadline.

**Format**

```
deadline <task> /by <date and time>
```

**Examples**

```
deadline Submit report /by 2024-03-15
deadline Tax filing /by 15 Mar 2024 23:59
deadline Write blog /by tomorrow 11:00am
```

**Behaviour**

* Task is stored with its deadline and initially marked undone.
* Date/time parsing accepts multiple formats (see *Supported Date/Time Formats* below).
* Duplicate detection is applied (task type + name + deadline considered).

---

### Add an event: `event`

Schedule an event that has a start and end time.

**Format**

```
event <event> /from <start date and time> /to <end date and time>
```

**Examples**

```
event Team meeting /from 2024-03-20 14:00 /to 2024-03-20 16:00
event Concert /from Fri 7pm /to Fri 10pm
event Travel to Malaysia /from 20/06/2024 /to 22/06/2024
```

**Behaviour**

* Events can span multiple days.
* Date/time parsing accepts multiple formats (see *Supported Date/Time Formats* below).
* Duplicate detection applies (task type + name + time frame considered).

---

### Remove a task/event: `delete`

Delete a task (todo / deadline / event) by its index as shown in `list` or `find`.

**Format**

```
delete <index>
```

**Example**

```
delete 3
```

**Behaviour**

* Removes the task from storage. You will receive a confirmation message with the deleted task details.

---

### List down all tasks/events: `list`

List all tasks in the current storage, showing index, type, status, name and (where applicable) deadline / start & end times.

**Format**

```
list
```

**Behavior**

* Outputs each task with its index.

---

### Find tasks/events by keyword: `find`

Find tasks and events whose name contains the keyword.

**Format**

```
find <keyword>
```

**Examples**

```
find homework
find Report
```

**Behaviour**

* **Case-sensitive** search of the task name
* Matches if the keyword appears anywhere in the task name (substring match).
* Returns a filtered list (with indexes relative to that filtered list).

---

### Complete and reset tasks: `mark` / `unmark`

Mark a task as done or reset it to undone.

**Format**

```
mark <index>
unmark <index>
```

**Example**

```
mark 2
unmark 2
```

**Behaviour**

* `mark` will set the task to done and display a celebratory Sheogorath quip.
* `unmark` sets it back to undone.

---

### `bye`

Close the chatbot.

**Format**

```
bye
```

**Behavior**

* Saves current tasks to disk and exits the program.

---

## Automatic save & load

All task data are **automatically saved** whenever you add/delete/mark/unmark tasks, and **automatically loaded** when the app boots.

---

## Supported Date/Time Formats

Sheogorath's parser for date and time is very forgiving. The app supports:

**Date formats**

* `yyyy-mm-dd` → `2024-03-15`
* `yyyy/mm/dd` → `2024/03/15`
* `dd MMM yyyy` → `15 Mar 2024`
* `dd/mm/yyyy` → `15/03/2024`
* Case-insensitive day names (and their abbreviations) / relative keywords:

    * `monday`, `fri`, `tue`, `TUES`, `Thursday`
    * `today`, `tomorrow`

**Time formats**

* 24-hour: `14:00`, `23:59`
* 12-hour: `2:30pm`, `11:00 AM`, `03:20 Pm`
* Special words: `noon` (→ `12:00`), `midnight` (→ `00:00`)

**Combined examples**

* `2024-03-20 14:00`
* `20 Mar 2024 2:00pm`
* `tomorrow 09:00`
* `fri 7:00pm`
* `Tues 11:00am`

**Notes**

If a deadline or event time frame is provided without a valid date and time, the time defaults to today.

---

## Duplicate detection

* The app checks for duplicates on insertion. A duplicate is defined as a task with the same type and identical normalized content (name and, for deadline/event, the times).
* On detecting a duplicate the app rejects the addition and shows a clear warning and the existing matching task,
