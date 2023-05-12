import tkinter as tk
from tkinter import filedialog
from tkinter import ttk
import subprocess
import os

#global_variables
entries = []
entries_converter = []
entries_mapping = []
PROGRAM_NAME = "CodeMetropolis Tester Interface "
VERSION = "v0.4.5"

#choose_file
def select_file(entry):
    file_path = filedialog.askopenfilename()
    #remove_text_from_entry 
    entry.delete(0, tk.END)
    #fill_entry_with_text
    entry.insert(0, file_path)

#choose_folder
def select_folder(entry):
    folder_path = filedialog.askdirectory()
    entry.delete(0, tk.END)
    entry.insert(0, folder_path)

#command_line_with_arhuments
def run_cmd_command():
    
    arg1 = entries[0].get()
    arg2 = entries[1].get()
    arg3 = entries[2].get()
    arg4 = entries[3].get()

    cmd = f"\"CodeMetropolis Tester.py\" {arg1} {arg2} {arg3} {arg4}"

    if len(entries_converter) == 2:
        arg5 = "--type " + entries_converter[0].get()
        if not entries_converter[1].get().strip():
            arg6 = "--parameters " + "None"
        else:
            arg6 = "--parameters " + entries_converter[1].get()
        cmd += f" {arg5} {arg6}"

    if len(entries_mapping) == 1:
        arg5 = "--mapping_file_path " + entries_mapping[0].get()
        cmd += f" {arg5}"

    subprocess.call(cmd, shell=True)
    #entries_converter.clear()
    #entries_mapping.clear()

#window
window = tk.Tk()
window.geometry("800x400")
window.title( PROGRAM_NAME + VERSION)
window.resizable(width=False, height=False)

#dict
gridNSEW = {"padx": 10, "pady": 10, "sticky": "nsew"}
gridEW = {"padx": 10, "pady": 10, "sticky": "ew"}

#create a menubar
menubar = tk.Menu(window)

#create file menu
filemenu = tk.Menu(menubar, tearoff=0)
filemenu.add_command(label="Converter", command=lambda: (change_main_page("Converter")))
filemenu.add_command(label="Mapping", command=lambda: (change_main_page("Mapping")))
filemenu.add_command(label="Placing", command=lambda: (change_main_page("Placing")))
filemenu.add_command(label="Rendering", command=lambda: (change_main_page("Rendering")))

#add the file menu to the menubar
menubar.add_cascade(label="Tool", menu=filemenu)

#create a help menu
helpmenu = tk.Menu(menubar, tearoff=0)
helpmenu.add_command(label="How to...", command=lambda: change_main_page("How_to"))
helpmenu.add_command(label="About", command=lambda: change_main_page("About"))

#add the help menu to the menubar
menubar.add_cascade(label="Help", menu=helpmenu)

#display the menubar
window.config(menu=menubar)

def basic_for_every_tool():
    arg_labels = ["Pytest file or folder:", "Input file or folder:", "Expected file or folder:", "Output folder:"]
    row_index = 1

    for i, label_text in enumerate(arg_labels):
        label = tk.Label(window, text=label_text)
        label.grid(row=row_index+i, column=0, **gridNSEW)

        entry = tk.Entry(window, name="entry" + str(i))
        entry.grid(row=row_index+i, column=1, **gridEW)
        entries.append(entry)
        
        if (i < 3):
            button = tk.Button(window, text="Select File", command=lambda e=entry: select_file(e))
            button.grid(row=row_index+i, column=2, **gridNSEW)

        folder_button = tk.Button(window, text="Select Folder", command=lambda e=entry: select_folder(e))
        if (i == 3):
            folder_button.grid(row=row_index+i, column=2, **gridNSEW)
        else:
            folder_button.grid(row=row_index+i, column=3, **gridNSEW)

def converter_tool_plus_arguments():

    converter_cb_options = ["SourceMeter", "Pmd", "Gitlab", "GitStats"]
    label = tk.Label(window, text="Type:")
    label.grid(row=5, column=0, **gridNSEW)

    entry = ttk.Combobox(window, values=converter_cb_options, state='readonly', name="entry4")
    entry.grid(row=5, column=1, **gridEW)
    entries_converter.append(entry)

    entry.current(0)

    label = tk.Label(window, text="Parameters: (Optional)")
    label.grid(row=6, column=0, **gridNSEW)

    entry = tk.Entry(window, name="entry5")
    entry.grid(row=6, column=1, **gridEW) 
    entries_converter.append(entry)

def mapping_tool_plus_arguments():
    label = tk.Label(window, text="Mapping file example:")
    label.grid(row=5, column=0, **gridNSEW)

    entry = tk.Entry(window, name="entry4")
    entry.grid(row=5, column=1, **gridEW) 
    entries_mapping.append(entry)

    button = tk.Button(window, text="Select File", command=lambda e=entry: select_file(e))
    button.grid(row=5, column=2, **gridNSEW)


def about_page():
    label = tk.Label(window, text= PROGRAM_NAME + VERSION, font=("Arial", 20))
    label.grid(row=0, column=2, **gridNSEW)

    label = tk.Label(window, text="A visual interface to perform regression tests by comparing expected output files with CodeMetropolis output files.\n\n Made by: Zoltan Banyai \n 2023", font=("Arial", 10))
    label.grid(row=1, column=2, **gridNSEW)

def how_to_page():
    label = tk.Label(window, text= "How to use " + PROGRAM_NAME, font=("Arial", 20))
    label.grid(row=0, column=2, **gridNSEW)

    label = tk.Label(window, text=" 1. Step - Choose a tool from \"tool\" menu \n 2. Step - Select a specific test file or a folder wich contain tests \n 3. Step - Select an input file wich compatible with tool \n 4. Step - Select an expected output file wich will be compare with tool output  \n 5. Step - Select a folder where tool will save it output \n 6. Step - If the converter tool is selected, you must specify the input type \n 7. Step - You can give optional parameters if have to and it's supported by tool \n 8. Step - Press run to use selected tool and run test", font=("Arial", 10), justify="left", anchor="w")
    label.grid(row=1, column=2, sticky = "w")

def show_run_button(page_name):
    run_button = tk.Button(window, text="Run", command=lambda: (run_cmd_command(), save_to_file(page_name), change_main_page(page_name)))
    run_button.grid(row=7, column=1, **gridNSEW)

about_page()   

def show_load_button(page_name):
    if (os.path.isfile("last_used_args.txt")):
        run_button = tk.Button(window, text="Load last args", command=lambda: (load_last_use_args(page_name)))
        run_button.grid(row=7, column=2, **gridNSEW)

def save_to_file(page_name):
    with open('last_used_args.txt', 'w') as f:
        for i in range (4):
            f.write(entries[i].get() + "\n")
        if (page_name == "Converter"):
            f.write(entries_converter[0].get() + "\n")
            f.write(entries_converter[1].get() + "\n")
        if (page_name == "Mapping"):
            f.write(entries_mapping[0].get() + "\n")
    entries.clear()
    entries_converter.clear()
    entries_mapping.clear()
    show_load_button(page_name)

def load_last_use_args(page_name):
    with open('last_used_args.txt', 'r') as f:
        lines = f.readlines()

    for i, line in enumerate(lines):
        entry_name = "entry" + str(i)
        if entry_name in window.children:
            entry = window.nametowidget(entry_name)
            entry.delete(0, tk.END)
            entry.insert(0, line.strip())
            if entry_name == "entry4" and page_name == "Converter":
                entry = window.nametowidget(entry_name)
                match line.strip():
                    case "SourceMeter":
                        entry.current(0)
                    case "Pmd":
                        entry.current(1)
                    case "Gitlab":
                        entry.current(2)
                    case "GitStats":
                        entry.current(3)


for i in range(5):
    window.grid_columnconfigure(i, weight=1)
for i in range(8):
    window.grid_rowconfigure(i, weight=1)

def change_main_page(page_name):
    for widget in window.grid_slaves():
        widget.grid_forget()
    match page_name:
        case "Converter":
            basic_for_every_tool()
            converter_tool_plus_arguments()
            show_run_button(page_name)   
            show_load_button(page_name)
            entries_mapping.clear()
            
        case "Mapping":
            basic_for_every_tool()
            mapping_tool_plus_arguments()
            show_run_button(page_name)
            show_load_button(page_name)
            entries_converter.clear()

        case "Placing":
            basic_for_every_tool()
            show_run_button(page_name)
            show_load_button(page_name)
            entries_converter.clear()
            entries_mapping.clear()

        case "Rendering":
            basic_for_every_tool()
            show_run_button(page_name)
            show_load_button(page_name)
            entries_converter.clear()
            entries_mapping.clear()

        case "About":
            about_page()

        case "How_to":
            how_to_page()

        case _:
            about_page()
        

window.mainloop()
